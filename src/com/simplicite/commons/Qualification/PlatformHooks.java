package com.simplicite.commons.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.util.exceptions.*;

/**
 * Platform Hooks
 */
public class PlatformHooks extends com.simplicite.util.engine.PlatformHooksInterface {

    @Override
    public void preLoadGrant(Grant g) {

        String login = g.getLogin();
        Grant sys = Grant.getSystemAdmin();

        if (login.contains("simplicite")) {
            String userRowId = sys.simpleQuery("select row_id from m_user where usr_login = '" + login + "'");
            Grant.addResponsibility(userRowId, "QUAL_ADMIN", null, null, true, "ApplicationUsers");
            AppLog.info(GrantHooks.class, "preLoadGrant",
                    "Added " + "QUAL_ADMIN" + " responsibility for user " + userRowId, sys);
            AppLog.info(g.toString(), null);
        }

        /*
         * if (g.isOAuth2AuthMethod() && (g.getAuthProvider()).contains("google")) try {
         * // only if OAuth2 auth method if (Tool.isEmpty(login)) throw new
         * AuthenticationException("Empty login");
         * 
         * String group = sys.getParameter("DEFAULT_USER_GROUP", "QUAL_ADMIN");
         * g.addResponsibility(g.getUserUniqueId(), "QUAL_ADMIN",
         * Tool.getCurrentDate(-1), "", true, "ApplicationUsers");
         * AppLog.info(GrantHooks.class, "preLoadGrant", "Added " + "QUAL_ADMIN" +
         * " responsibility for user " + login, sys);
         * 
         * 
         * 
         * 
         * 
         * 
         * // Create user if needed if (!Grant.exists(login, true)) {
         * AppLog.info("Creating User", null); ObjectDB usr =
         * sys.getTmpObject("QualUser"); synchronized (usr) { // thread-safe
         * usr.resetValues(true); usr.setRowId(ObjectField.DEFAULT_ROW_ID);
         * usr.getField("usr_login").setValue(login);
         * usr.setFieldValue("qualUsrTypedutilisateur", "ADMIN");
         * //usr.setFieldValue("usr_lang", Globals.LANG_FRENCH);
         * usr.setFieldValue("row_module_id", ModuleDB.getModuleId("ApplicationUsers"));
         * usr.setStatus(Grant.USER_ACTIVE); try{ new
         * BusinessObjectTool(usr).validateAndCreate(); String module =
         * usr.getFieldValue("row_module_id.mdl_name"); AppLog.info(GrantHooks.class,
         * "preLoadGrant", "Created user " + login + " in module " + module, sys);
         * 
         * if (!Tool.isEmpty(group)) { Grant.addResponsibility(usr.getRowId(), group,
         * Tool.getCurrentDate(-1), "", true, module); AppLog.info(GrantHooks.class,
         * "preLoadGrant", "Added " + group + " responsibility for user " + login, sys);
         * } } catch(ValidateException e){ AppLog.error(e, null); }
         * catch(CreateException e){ AppLog.error(e, null); }
         * 
         * } } } catch (Exception e) { AppLog.error(GrantHooks.class, "preLoadGrant",
         * null, e, sys); }
         */
    }

    @Override
    public void postLoadGrant(Grant g) {

        AppLog.info(g.toString(), null);
    }

}
