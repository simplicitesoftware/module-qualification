package com.simplicite.extobjects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;
import com.simplicite.webapp.services.RESTServiceExternalObject;

import com.simplicite.util.exceptions.*;

import com.simplicite.objects.Qualification.QualUser;

/**
 * External object QualCreateCandidate
 */
public class QualCreateCandidate extends RESTServiceExternalObject {
	private static final long serialVersionUID = 1L;

	@Override
    public Object get(Parameters params){
    	JSONObject data = new JSONObject();
    	data.put("webservice_status", "OK");
    	data.put("url_param", params.getParameter("url_param", "empty"));
        return data;
    }
    
    @Override
    public Object post(Parameters params){
		
		Grant g = getGrant();
		
		
    	/*JSONObject data = new JSONObject();
    	data.put("webservice_status", "OK");
    	data.put("posted_param", params.getParameter("post_param", "empty"));*/
    	JSONObject data = params.getJSONObject();
    	
		try{
			QualUser stag = (QualUser) g.getTmpObject("QualUser");
			BusinessObjectTool stagBot = stag.getTool();
			stagBot.getForCreate();
			
			stag.setFieldValue("usr_login", data.getString("usr_login"));
			stag.setFieldValue("usr_email", data.getString("usr_email"));
			stag.setFieldValue("usr_lang", data.getString("usr_lang"));
			stag.setFieldValue("qualUsrTypedutilisateur", "CAND");
			
			stagBot.validateAndCreate();
			
			data.put("candidateUrl", stag.getFieldValue("qualUsrUrlQuest"));
			data.put("directUrl", stag.getDirectURL(true));
			
			//Send notification email
			stag.qualNotify();
			/*try{
				synchronized(stag){
					if(stag.select(stag.getRowId())){
						stag.invokeAction("QualCandidateNotify");
					}
				}
			}
			catch(ActionException e){
				AppLog.error("invokeAction", e, getGrant());
			}*/
			
			List<String[]> examIds = g.query("select row_id from qual_exam where qual_exam_name LIKE '%QCM%' and qual_ex_type LIKE 'SIM_%';");
			
			for(String[] id : examIds){
				ObjectDB usrExSub = g.getTmpObject("QualUsrExamSubjects");
				BusinessObjectTool uesTool = usrExSub.getTool();
				uesTool.getForCreate();
				
				usrExSub.setFieldValue("qualUsrexamUsrId", stag.getRowId());
				usrExSub.setFieldValue("qualUsrexamExamId", id[0]);
				
				uesTool.validateAndCreate();
			}
			
			
			
		}
		catch(Exception e){
			AppLog.error("ERROS", e, getGrant());
		} 
		

        return data;
    }
}
