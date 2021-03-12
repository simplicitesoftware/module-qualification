package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import com.simplicite.commons.Qualification.*;

/**
 * Business object QualExUsr
 */
public class QualExUsr extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		Grant g = getGrant();
		
		if(isCandidate(g)){
			for(ObjectField f : getFieldArea("QualExUsr-2").getFields()){
				f.setVisibility(ObjectField.VIS_FORBIDDEN);
			}
			getField("qualExAnswerText").setVisibility(ObjectField.VIS_FORBIDDEN);
			String searchSpec = "t.row_id in (select row_id from qual_ex_usr ans join qual_user_exam ex on ans.QUAL_EXUSR_USREXAM_ID = ex.row_id where QUAL_USREXAM_USR_ID = '"+g.getUserId()+"')";
			setSearchSpec(searchSpec);
		}
		
	}
	
	
	@Override
	public void initList(ObjectDB parent) {
		
		Grant g = getGrant();
		if(isCandidate(g)){	
			for(ObjectField f : getFieldArea("QualExUsr-1").getFields()){
				f.setVisibility(ObjectField.VIS_HIDDEN);
			}
		}
	}
	
	@Override
	public boolean isUpdateEnable(String[] row) {
		
		boolean notSubmitted = "0".equals(getFieldValue("qualExusrSubmitted", row));
		boolean notOver = !QualUserExam.isExamOver(getFieldValue("qualUsrexamDateLimite", row));
		
		return isCandidate(getGrant()) ? (notSubmitted && notOver) : true;
		
	}
	
	@Override
	public void initUpdate() {
		
		Grant g = getGrant();
		
		getFieldArea("QualExUsr-1").setVisible(!isCandidate(g));
		for(ObjectField f : getFieldArea("QualExUsr-3").getFields()){
			f.setUpdatable("0".equals(getFieldValue("qualExusrSubmitted")));
		}
		for(ObjectField of : getFieldArea("QualExUsr-4").getFields()){
			of.setUpdatable(false);
		}	
		
		String sqlTotal = "select count(*) from qual_ex_usr where qual_exusr_usrexam_id = '"+getFieldValue("qualExusrUsrexamId")+"'";
		String sqlCurrent = "select count(*) from qual_ex_usr where qual_exusr_usrexam_id = '"+getFieldValue("qualExusrUsrexamId")+"' and qual_exusr_submitted = '1'";
		double total = Double.parseDouble(g.simpleQuery(sqlTotal));
		double current = Double.parseDouble(g.simpleQuery(sqlCurrent));
		double progress = (current / total) * 100;
		setFieldValue("qualExusrProgress", progress);
		
	}
	
	
	@Override
	public boolean isActionEnable(String[] row, String action) {
		
		if("QUAL_SUBMITANSWER".equals(action)){
			boolean notSubmitted = "0".equals(getFieldValue("qualExusrSubmitted", row));
			boolean notOver = !QualUserExam.isExamOver(getFieldValue("qualUsrexamDateLimite", row));
			return notSubmitted && notOver;
		}
		return true;
	}
	
	public String submitAnswer(){
		
		//if type enum, check answer with correct answer in ex definition
		if("ENUM".equals(getFieldValue("qualExusrExId.qualExAnswerType"))){
			
			String correctAnswer = getFieldValue("qualExusrExId.qualExAnswerEnumeration").replaceAll("(^\\h*)|(\\h*$)|\\s", "");
			String submittedAnswer = getFieldDisplayValue("qualExusrAnswerEnumeration").replaceAll("(^\\h*)|(\\h*$)|\\s", "");
			setFieldValue("qualExusrCheck",correctAnswer.equals(submittedAnswer) ? "OK" : "KO");
		}
		
		setFieldValue("qualExusrSubmitted", "1");
		save();
				
		return displayNext(getRowId());

	}
	
	
	public static boolean isCandidate(Grant g){
		return QualTool.isCandidate(g);
	}
	
	private String displayNext(String rowId){
		
		Grant g = getGrant();
		
		String userExamRowId = getFieldValue("qualExusrUsrexamId");
		
		String nextRow = g.simpleQuery("select row_id from qual_ex_usr where qual_exusr_usrexam_id = '"+userExamRowId+"' and row_id > "+rowId + " and QUAL_EXUSR_SUBMITTED = '0' limit 1");
		String prevRow = g.simpleQuery("select row_id from qual_ex_usr where qual_exusr_usrexam_id = '"+userExamRowId+"' and row_id < "+rowId + " and QUAL_EXUSR_SUBMITTED = '0' limit 1");
		if(nextRow == "" && prevRow == ""){
			//Test is done - set state
			ObjectDB userExam = g.getTmpObject("QualUserExam");
			
			synchronized(userExam){
				
				if(userExam.select(userExamRowId)){
					userExam.setFieldValue("qualUsrexamEtat", "DONE");
					BusinessObjectTool bot = new BusinessObjectTool(userExam);
					try{
						bot.validateAndSave();	
					}
					catch(Exception e){
						AppLog.error(getClass(), "displayNext", "Validate and save error", e, g);
					}
					
				}
				
			}
			return HTMLTool.redirectStatement("/ui/ext/QualStartTestExt"); 
		}
		else if(nextRow != ""){
			rowId = nextRow;
		}
		else if(prevRow != ""){
			rowId = prevRow;
		}
		
		return HTMLTool.redirectStatement("/ui/obj/form/QualExUsr?row_id=" +rowId);
		
	}
	
}