package com.simplicite.extobjects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;
import com.simplicite.webapp.web.*;
/**
 * External object QualPostCertif
 */
public class QualPostCertif extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		
		try {
			Grant g = getGrant();
					
			boolean pub = isPublic();
			setDecoration(!pub);
			
			String token = params.getParameter("token");
			
			//Get rowid of Certification linked to token passed in url
			String certifId = g.simpleQuery("select row_id from qual_cert_usr where qual_certusr_url_eval LIKE '%"+token+"'");
			
			//Get rowid of user linked to token passed in url
			String userId = g.simpleQuery("select row_id from m_user where qual_usr_token = '"+token+"'");
			
			ObjectDB examEx = g.getTmpObject("QualExamEx");
			examEx.resetValues();
			examEx.resetFilters();
			
			String examId = "";
			
			JSONArray exams = new JSONArray();
			
			//Get certif exams linked to the user (created when linking a user to a certification)
			List<String[]> usrCertifExams = g.query("select row_id, QUAL_USREXAM_EXAM_ID from qual_user_exam where QUAL_USREXAM_CERTUSR_ID = "+certifId);
						
			JSONArray userExamIdsArray = new JSONArray();
			
			for(String[] userExamIds : usrCertifExams){
				
				examId = userExamIds[1];
				userExamIdsArray.put(userExamIds[0]);
				
				ObjectDB examObj = g.getTmpObject("QualExam");
				
				if(examObj.select(examId)){
					
					String examName = examObj.getFieldValue("qualExamName");
					String examDescription = examObj.getFieldValue("qualExamDescription");
					
					JSONObject exam = new JSONObject();
					JSONArray qsts = new JSONArray();
					JSONObject answers = new JSONObject();
					//JSONArray answers = new JSONArray();
					if(!"".equals(examId)){
						examEx.setFieldFilter("qualExamexExamId", examId);
						
						JSONObject start = new JSONObject();
						start.put("type", "QST_BREAK");
						qsts.put(start);
						
						for(String[] row : examEx.search()){
							
							examEx.setValues(row);
							JSONObject qst = new JSONObject();
							qst.put("examTitle", examEx.getFieldValue("qualExamexExamId.qualExamName"));
							qst.put("title", examEx.getFieldValue("qualExamexExId.qualExQuestion"));
							qst.put("type", examEx.getFieldValue("qualExamexExId.qualExAnswerType"));
							qst.put("enum", examEx.getFieldValue("qualExamexExId.qualExChoicesEnumeration"));
							qst.put("id", examEx.getFieldValue("qualExamexExId.qualExId"));
							qsts.put(qst);
							
							answers.put(examEx.getFieldValue("qualExamexExId.qualExId"), examEx.getFieldValue("qualExamexExId.qualExAnswerEnumeration"));
							
						}
						
						exam.put("answers", answers);
						
						exam.put("questions", qsts);
						exam.put("examTitle", examName);
						exam.put("examId", userExamIds[0]);
						exam.put("examDescription", examDescription);
						
						
						
					}
					exams.put(exam);
					
				}
				
			}
						
			String template = HTMLTool.getResourceHTMLContent(this, "HTML_POST_CERTIF_TEMPLATE");
			JSONObject renderParams = params.toJSONObject().put("pub", pub).put("exams", exams);
			
			renderParams.put("userId", userId);
			renderParams.put("userExamIds", userExamIdsArray);
			
			String render = getName() + ".render(" + renderParams.toString() +",'"+template.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n")+ "');";
			if (pub) { // Public page version (standalone Bootstrap page)
				BootstrapWebPage wp = new BootstrapWebPage(params.getRoot(), getDisplay());
				wp.setFavicon(HTMLTool.getResourceIconURL(this, "FAVICON"));
				wp.appendAjax(true);
				wp.appendVue();
				wp.appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
				wp.appendCSSInclude(HTMLTool.getResourceCSSURL(this, "STYLES"));
				wp.append(HTMLTool.getResourceHTMLContent(this, "HTML"));
				
				
				wp.setReady(render);
				return wp.toString();
			} else { // Private page version
				appendVue();
				return javascript(render);
			}
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}
