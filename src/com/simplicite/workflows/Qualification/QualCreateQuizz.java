package com.simplicite.workflows.Qualification;

import java.util.*;
import com.simplicite.bpm.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.util.exceptions.*;

/**
 * Process QualCreateQuizz
 */
public class QualCreateQuizz extends Processus {
	private static final long serialVersionUID = 1L;
	
	String examId = null;
	ArrayList<String> examExIds = new ArrayList<>();
	
	@Override
	public void postValidate(ActivityFile context) {
		String step = context.getActivity().getStep();
		switch(step){
			//CreateQuizz
			case "CQUIZZ-100":
				DataFile df = context.getDataFile("Field", "row_id", false);
				examId = (df!=null)?df.getValue(0):"";
				break;
			//AddQuestion step
			case "CQUIZZ-200":
				createExamQuestions(context);
				break;
			//AddUser step
			case "CQUIZZ-600":
				createUserExams(context);
				break;
			default:
				//doNothing
		}
	}
	
	private void createExamQuestions(ActivityFile context){
		Grant g = getGrant();
		
		String[] rowIds = getSelectedRowIds(context);
		for (int i=0; rowIds!=null && i<rowIds.length; i++){
			
			String exerciceRowId = rowIds[i];
			
			AppLog.info("Exam ID : "+ examId + " exercice ID : "+ exerciceRowId, getGrant());
			
			ObjectDB examEx = g.getTmpObject("QualExamEx");
			BusinessObjectTool examExT = examEx.getTool();
			
			try{
				examExT.getForCreate();
				
				examEx.setFieldValue("qualExamexExId", exerciceRowId);
				examEx.setFieldValue("qualExamexExamId", examId);
				
				examExT.validateAndCreate();
				
				examExIds.add(examEx.getRowId());
			}
			catch(GetException e){
				
			}
			catch(ValidateException e){
				
			}
			catch(CreateException e){
				
			}
		}
	}
	
	private void createUserExams(ActivityFile context){
		Grant g = getGrant();
		
		String[] rowIds = getSelectedRowIds(context);
		for (int i=0; rowIds!=null && i<rowIds.length; i++){
			
			String userId = rowIds[i];
				
			ObjectDB userSubs = g.getTmpObject("QualUsrExamSubjects");
			BusinessObjectTool userSubsT = userSubs.getTool();
			
			try{
				userSubsT.getForCreate();
				
				userSubs.setFieldValue("qualUsrexamUsrId", userId);
				userSubs.setFieldValue("qualUsrexamExamId", examId);
				
				userSubsT.validateAndCreate();
				
			}
			catch(GetException e){
				
			}
			catch(ValidateException e){
				
			}
			catch(CreateException e){
				
			}
				
		}
	}
	
	private String[] getSelectedRowIds(ActivityFile context){
		
		DataFile df = context.getDataFile("Field","row_id",true);
		return df!=null ? df.getValues() : null;
		
	}
	
	
}
