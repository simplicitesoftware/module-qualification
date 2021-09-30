package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import java.time.LocalDate;
import com.simplicite.objects.System.User;

/**
 * Business object QualUser
 */
public class QualUser extends com.simplicite.objects.System.SimpleUser {
	private static final long serialVersionUID = 1L;
	
		
	@Override
	public void postLoad() {
		super.postLoad();
		// hide most of the SimpleUser fields, keeping only email & login
		hideFatherFields();
	}				
	
	
	 private void hideFatherFields() {

        for (FieldArea fA : getFieldAreas()) {
            if (fA.getName().startsWith("User"))
                fA.setVisible(false);
        }

    }
	
	@Override
    public String postCreate() {

        resetResps();
        
        if(isCandidate()){
        	//generateCandidateTests(this);
        }
        return super.postCreate();
    }
    
    @Override
    public List<String> preValidate() {
    	List<String> msgs = new ArrayList<>();
    	
    	if(isNew()){
    		setFieldValue("row_module_id", ModuleDB.getModuleId("ApplicationUsers"));
    	}
    	
    	if("".equals(getFieldValue("qualUsrToken")))
    		setFieldValue("qualUsrToken", Tool.randomUUID());
    	
    	if("".equals(getFieldValue("qualUsrUrlQuest")))
    		setFieldValue("qualUsrUrlQuest", "https://qualification5.dev.simplicite.io/ext/QualPostTraining?token="+getFieldValue("qualUsrToken"));
    	
    	return msgs;
    }
    
    @Override
    public List<String> postValidate() {
    	List<String> msgs = new ArrayList<>();
    	
    	if(isCandidate()){
    		setFieldValue("usr_menu", "0");
    	}
    	
    	return msgs;
    }
    
    /*
	Email is sent to candidate after it is registered 
	*/
   /* @Override
    public String postUpdate() {
    	
    	ObjectField active = getField("usr_active");
    	if(active.hasChanged() && "1".equals(active.getValue())){
    		
    		// New password
			String pwd = resetUsrPwd(getFieldValue("usr_login"));
			
			// Token to change password		
			getGrant().setUserSystemParam(getFieldValue("usr_login"), Globals.FORCE_CHANGE_PASSWORD, "yes", true);
    		
    		sendMailNotif(pwd);
    	}
    	
    	return null;
    }*/
    
    private void generateCandidateTests(ObjectDB user){
    	
    	Grant g = getGrant();
    	ObjectDB exam = g.getTmpObject("QualExam");
    	exam.resetFilters();
    	exam.setFieldFilter("qualExType", getFieldValue("qualUsrTests"));
    	
    	for(String[] rslt : exam.search()){
    		exam.setValues(rslt, false);
    		
    		ObjectDB usrExam = g.getTmpObject("QualUserExam");
    		usrExam.resetValues();
    		usrExam.setFieldValue("qualUsrexamUsrId", user.getRowId());
    		usrExam.setFieldValue("qualUsrexamExamId", exam.getRowId());
    		String id = user.getFieldValue("usr_login")+"-"+exam.getFieldValue("qualExamName")+"-"+Tool.getCurrentDateTime();
    		AppLog.info(getClass(), "generateCandidateTests", id, getGrant());
    		usrExam.setFieldValue("qualUsrexamId", id);
    		LocalDate currentDate = LocalDate.parse(Tool.getCurrentDate());
    		String ech = currentDate.plusWeeks(1).toString();
    		usrExam.setFieldValue("qualUsrexamDateLimite", ech);
			
			try{
				BusinessObjectTool bot = new BusinessObjectTool(usrExam);
				bot.validateAndCreate();
			}
			catch(Exception e){
				AppLog.error(getClass(), "generateTests", "postCreate", e, getGrant());
			}
    	}
    }
    
    private boolean isCandidate(){
    	return "CAND".equals(getFieldValue("qualUsrTypedutilisateur"));
    }
    
	private void sendMailNotif(String pwd){
		
		AppLog.info(getClass(), "sendMailNotif", "sendMailNotif", getGrant());
		Grant g = getGrant();
		MailTool m = new MailTool();
		
		m.addRcpt(getFieldValue("usr_email"));
		m.setSubject("[Simplicité] Questionnaire post-formation");
		
	/*	List<String> bcc = new ArrayList<String>(Arrays.asList("awheeler@simplicite.fr", "trepolt@simplicite.fr", "cyrille.de-benque@capgemini.com"));
		m.addBcc(bcc);*/
		
        String url = g.getSystemParam("DIRECT_URL");

		String c = HTMLTool.getResourceHTMLContent(this, "QUAL_USR_NOTIF");

        /*c = c.replace("[NOM]", getFieldValue("usr_last_name"));
        c = c.replace("[PRENOM]", getFieldValue("usr_first_name"));
        c = c.replace("[LOGIN]", getFieldValue("usr_login"));
        c = c.replace("[PWD]", pwd);*/
        c = c.replace("[URL]", url + "/ext/QualPostTraining?token="+getFieldValue("qualUsrToken"));
		
		m.setBody(c);
		m.send();		
	}
	
	private String resetUsrPwd(String login){
		
		Grant grant = getGrant();
		boolean[] rights = grant.changeAccess("QualUser", true, true, true, true);
		AppLog.info(getClass(), "resetUsrPwd", "===========SETTING PASSWORD============", getGrant());
		String userLogin = login;
		String newPassword = grant.changePassword(userLogin, null, true, true);
		AppLog.info(getClass(), "resetUsrPwd", newPassword, getGrant());
		grant.changeAccess("QualUser", rights);
		return newPassword;
		
	}
	
	private void createTestElement(ObjectDB ex, String[] row) throws Exception{
		
			ex.setValues(row, false);
			ObjectDB test = getGrant().getTmpObject("QualExUsr");
			test.setFieldValue("qualUsrexamUsrId", getRowId());
			test.setFieldValue("qualUsrexamExamId", ex.getRowId());
			BusinessObjectTool bot = new BusinessObjectTool(test);
			bot.validateAndCreate();
			
	}
	
	
	public String[] getRandomElement(List<String[]> list){ 
		
        Random rand = new Random(); 
        return list.get(rand.nextInt(list.size())); 
        
    }
    
    private void resetResps() {

        // Reset all responsibility
        Grant.removeResponsibility(getRowId(), "QUAL_CANDIDATE");
        Grant.removeResponsibility(getRowId(), "QUAL_ADMIN");

        String role = getFieldValue("qualUsrTypedutilisateur");

        if (role.contains("CAND")) {
            Grant.addResponsibility(getRowId(), "QUAL_CANDIDATE", null, null, true, "ApplicationUsers");
        }
        if (role.contains("ADMIN")) {
            Grant.addResponsibility(getRowId(), "QUAL_ADMIN", null, null, true, "ApplicationUsers");
        }
    }
	
	public String generateTests(){
		//Notify
		sendMailNotif("");
		
		return Message.formatSimpleInfo("Utilisateur notifié");
		
	}
	
	/*Replaced to simply notify user
	public String generateTests(){
		
		Grant g = getGrant();
		
		//Get tests and levels defined for candidate
		String[] levels = getFieldValue("qualUsrLevel").split(";");
		String[] tests = getFieldValue("qualUsrTests").split(";");
		
		//Get exercises that match the candidate's criteria
		ObjectDB ex = g.getTmpObject("QualExercise");
		
		for(int i = 0; i<levels.length; i++){
			
			for(int j = 0; j<tests.length; j++){
				
					ex.resetFilters();
					ex.setFieldFilter("qualExType", tests[j]);
					ex.setFieldFilter("qualExDifficulty", levels[i]);
					
					List<String[]> rslts = ex.search();
		
					if(!Tool.isEmpty(rslts)){
						
						for(int k = 0; k<5; k++){
							String[] row = getRandomElement(rslts);
							try{
								createTestElement(ex, row);
							}
							catch(Exception e){
								AppLog.error(getClass(), "generateTests", "generateTests", e, getGrant());
							}
						
						}
					}
			}
			
		}
		
		return Message.formatSimpleInfo("Test created");
	}*/
	
	
	
}
