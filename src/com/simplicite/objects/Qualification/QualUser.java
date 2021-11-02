package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import com.simplicite.util.exceptions.*;

import com.simplicite.objects.System.User;

/**
 * Business object QualUser
 */
public class QualUser extends com.simplicite.objects.System.SimpleUser {

    private static final long serialVersionUID = 1L;
    private Random rand = new Random();

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

        return super.postCreate();
    }

    @Override
    public List<String> preValidate() {

        List<String> msgs = new ArrayList<>();

        if (isNew()) {
            setFieldValue("row_module_id", ModuleDB.getModuleId("ApplicationUsers"));
        }

        if ("".equals(getFieldValue("qualUsrToken")))
            setFieldValue("qualUsrToken", Tool.randomUUID());

        if ("".equals(getFieldValue("qualUsrUrlQuest")))
            setFieldValue("qualUsrUrlQuest", "https://qualification5.dev.simplicite.io/ext/QualPostTraining?token="
                    + getFieldValue("qualUsrToken"));

        return msgs;
    }

    @Override
    public List<String> postValidate() {

        List<String> msgs = new ArrayList<>();

        if (isCandidate()) {
            setFieldValue("usr_menu", "0");
        }

        return msgs;
    }

    @Override
    public String postUpdate() {

        resetUsrPwd(getFieldValue("usr_login"));

        // return Message.formatInfo("INFO_CODE", "Message", "fieldName");
        // return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
        // return Message.formatError("ERROR_CODE", "Message", "fieldName");
        // return HTMLTool.redirectStatement(HTMLTool.getFormURL("Object", null, "123",
        // "nav=add"));
        // return HTMLTool.redirectStatement(HTMLTool.getListURL("Object", null,
        // "nav=add"));
        // return HTMLTool.javascriptStatement("/* code */");
        return null;
    }

    private boolean isCandidate() {

        return "CAND".equals(getFieldValue("qualUsrTypedutilisateur"));
    }

    private void sendMailNotif() {

        AppLog.info(getClass(), "sendMailNotif", "sendMailNotif", getGrant());
        Grant g = getGrant();
        MailTool m = new MailTool();

        m.addRcpt(getFieldValue("usr_email"));
        m.setSubject("[Simplicité] Questionnaire post-formation");

        /*
         * List<String> bcc = new
         * ArrayList<String>(Arrays.asList("nseitz@simplicite.fr")); m.addBcc(bcc);
         */

        String url = g.getSystemParam("DIRECT_URL");

        String c = HTMLTool.getResourceHTMLContent(this, "QUAL_USR_NOTIF");

        /*
         * c = c.replace("[NOM]", getFieldValue("usr_last_name")); c =
         * c.replace("[PRENOM]", getFieldValue("usr_first_name")); c =
         * c.replace("[LOGIN]", getFieldValue("usr_login")); c = c.replace("[PWD]",
         * pwd);
         */
        c = c.replace("[URL]", url + "/ext/QualPostTraining?token=" + getFieldValue("qualUsrToken"));

        m.setBody(c);
        m.send();
    }

    private String resetUsrPwd(String login) {

        Grant grant = getGrant();
        boolean[] rights = grant.changeAccess("QualUser", true, true, true, true);
        AppLog.info(getClass(), "resetUsrPwd", "===========SETTING PASSWORD " + login + " ============", getGrant());
        String userLogin = login;
        String newPassword = grant.changePassword(userLogin, "54Uyhghjk*", true, true);
        AppLog.info(getClass(), "resetUsrPwd", newPassword, getGrant());
        grant.changeAccess("QualUser", rights);
        return newPassword;

    }

    public String[] getRandomElement(List<String[]> list) {

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

    public String qualNotify() {

        // Notify
        sendMailNotif();
        return Message.formatSimpleInfo("Utilisateur notifié");

    }

}
