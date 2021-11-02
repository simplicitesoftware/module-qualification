package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.util.exceptions.*;

/**
 * Business object QualCertUsr
 */
public class QualCertUsr extends ObjectDB {

    private static final long serialVersionUID = 1L;

    @Override
    public List<String> preValidate() {

        List<String> msgs = new ArrayList<>();

        // msgs.add(Message.formatInfo("INFO_CODE", "Message", "fieldName"));
        // msgs.add(Message.formatWarning("WARNING_CODE", "Message", "fieldName"));
        // msgs.add(Message.formatError("ERROR_CODE", "Message", "fieldName"));

        if ("".equals(getFieldValue("qualCertusrUrlEval")))
            setFieldValue("qualCertusrUrlEval", "https://qualification5.dev.simplicite.io/ext/QualPostCertif?token="
                    + getFieldValue("qualUsrToken"));
        List<String[]> note = getGrant().query(
                "select sum(cast(QUAL_USREXAM_SCORE as int)), sum(cast(QUAL_USREXAM_TOTAL_POINTS as int)) from qual_user_exam where QUAL_USREXAM_ETAT='SCORED' and QUAL_USREXAM_CERTUSR_ID = "
                        + getRowId());
        setFieldValue("qualCertusrNote", Tool.isEmpty(note) ? "N/A" : (note.get(0)[0] + "/" + note.get(0)[1]));

        return msgs;
    }

    @Override
    public String postCreate() {

        // Get list of QualExam associated to Certif
        // For each item of list -> create QualUserExam with user and certif id

        Grant g = getGrant();
        String certifId = getFieldValue("qualCertusrCertId");
        String userId = getFieldValue("qualCertusrUsrId");

        List<String[]> examIds = g.query("select row_id from qual_exam where qual_exam_cert_id = " + certifId);
        for (String[] row : examIds) {
            String examId = row[0];
            ObjectDB usrExam = g.getTmpObject("QualUserExam");

            usrExam.resetValues();
            usrExam.setFieldValue("qualUsrexamUsrId", userId);
            usrExam.setFieldValue("qualUsrexamExamId", examId);
            usrExam.setFieldValue("qualUsrexamCertusrId", getRowId());
            usrExam.setFieldValue("qualUsrexamId", userId + "-" + examId + "-Certif-" + Tool.getCurrentDateTime());

            try {
                AppLog.info("CREATING " + userId + "-" + examId + "-" + certifId + "-" + Tool.getCurrentDateTime(),
                        getGrant());
                BusinessObjectTool bot = new BusinessObjectTool(usrExam);
                bot.validateAndCreate();
                AppLog.info("DONE CREATING", getGrant());

            } catch (ValidateException ve) {
                AppLog.error(ve, g);
            } catch (CreateException ce) {
                AppLog.info("ERROR CREATING", getGrant());
                AppLog.info(ce.getFullMessage().toString(), getGrant());
                AppLog.error(ce, g);
            }
        }
        return null;
    }

}
