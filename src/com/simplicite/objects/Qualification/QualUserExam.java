package com.simplicite.objects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.exceptions.CreateException;
import com.simplicite.util.exceptions.ValidateException;
import com.simplicite.util.tools.*;

import com.simplicite.commons.Qualification.*;
import java.time.LocalDate;

import org.json.*;

/**
 * Business object QualUserExam
 */
public class QualUserExam extends ObjectDB {

    private static final long serialVersionUID = 1L;

    @Override
    public void postLoad() {

        if (QualTool.isCandidate(getGrant())) {
            setSearchSpec("qual_usrexam_usr_id = " + getGrant().getUserUniqueId());
            getField("qualUsrexamId").setVisibility(ObjectField.VIS_HIDDEN);
            getField("usr_login").setVisibility(ObjectField.VIS_HIDDEN);
        }
    }

    @Override
    public String getUserKeyLabel(String[] row) {

        return getFieldValue("qualUsrexamUsrId.usr_first_name") + " "
                + getFieldValue("qualUsrexamUsrId.usr_last_name") + " - "
                + getFieldValue("qualUsrexamExamId.qualExamName");
    }

    @Override
    public List<String> preValidate() {

        List<String> msgs = new ArrayList<>();

        if (isNew() && !isTmpInstance()) {
            String id = getFieldValue("qualUsrexamUsrId.usr_login") + "-"
                    + getFieldValue("qualUsrexamExamId.qualExamName") + "-" + Tool.getCurrentDateTime();
            setFieldValue("qualUsrexamId", id);
        }

        if (!isNew() && "DONE".equals(getFieldValue("qualUsrexamEtat"))) {
            int score = calculateScore(getRowId()).optInt("score");
            int total = calculateScore(getRowId()).optInt("total");
            setFieldValue("qualUsrexamScore", score);
            setFieldValue("qualUsrexamTotalPoints", total);
            setFieldValue("qualUsrexamEtat", "SCORED");
        }

        return msgs;
    }

    @Override
    public boolean isUpdateEnable(String[] row) {

        return !QualExUsr.isCandidate(getGrant());
    }

    @Override
    public String postCreate() {

        Grant g = getGrant();

        ObjectDB exExam = g.getTmpObject("QualExamEx");

        exExam.resetValues();
        exExam.setFieldFilter("qualExamexExamId", getFieldValue("qualUsrexamExamId"));
        List<String[]> rslts = exExam.search();

        for (String[] row : rslts) {
            exExam.setValues(row, false);
            try {
                // createTestElement(exExam.getFieldValue("qualExamexExId"));
                createTestElement(exExam.getRowId());
            } catch (Exception e) {
                AppLog.error(getClass(), "postCreate", "Error creating test element", e, getGrant());
            }

        }

        return null;

    }

    private JSONObject calculateScore(String testId) {

        return isCertif(testId) ? calculateCertifScore(testId) : calculateQuizzScore(testId);

    }

    private JSONObject calculateCertifScore(String testId) {

        JSONObject scoreObj = new JSONObject();
        Grant g = getGrant();
        int score = 0;
        String totalQuery = "select sum(cast(ex.qual_ex_answer_enumeration as int)) " + "from qual_ex_usr q "
                + "join qual_exam_ex qex on q.QUAL_EXUSR_EXAMEX_ID = qex.row_id "
                + "join qual_exercise ex on qex.qual_examex_ex_id = ex.row_id " + "where qual_exusr_usrexam_id = "
                + testId + ";";

        String total = g.simpleQuery(totalQuery);

        String eltsQuery = "select q.QUAL_EXUSR_ANSWER, ex.qual_ex_answer_enumeration " + "from qual_ex_usr q "
                + "join qual_exam_ex qex on q.QUAL_EXUSR_EXAMEX_ID = qex.row_id "
                + "join qual_exercise ex on qex.qual_examex_ex_id = ex.row_id " + "where qual_exusr_usrexam_id = "
                + testId + ";";

        List<String[]> elts = g.query(eltsQuery);

        for (String[] answerElt : elts) {
            int answer = Integer.parseInt(answerElt[0]);
            int correctAnswer = Integer.parseInt(answerElt[1]);
            if (answer > correctAnswer) {
                score = score + correctAnswer;
            } else {
                score = score + answer;
            }
        }

        int totalScore = Integer.parseInt(total);
        scoreObj.put("total", totalScore);
        scoreObj.put("score", score);
        return scoreObj;
    }

    private JSONObject calculateQuizzScore(String testId) {

        JSONObject scoreObj = new JSONObject();
        Grant g = getGrant();

        String ok = "select sum(qual_examex_score) from qual_ex_usr q join qual_exam_ex qex on q.QUAL_EXUSR_EXAMEX_ID = qex.row_id where qual_exusr_usrexam_id = '"
                + testId + "' and QUAL_EXUSR_CHECK='OK';";
        AppLog.info("OK SCORE : " + g.simpleQuery(ok), g);
        String total = "select sum(qual_examex_score) from qual_ex_usr q join qual_exam_ex qex on q.QUAL_EXUSR_EXAMEX_ID = qex.row_id where qual_exusr_usrexam_id = '"
                + testId + "';";
        AppLog.info("TOTAL SCORE : " + g.simpleQuery(total), g);

        int totalScore = "".equals(g.simpleQuery(total)) ? 0 : Integer.parseInt(g.simpleQuery(total));
        int okScore = "".equals(g.simpleQuery(ok)) ? 0 : Integer.parseInt(g.simpleQuery(ok));

        scoreObj.put("total", totalScore);
        scoreObj.put("score", okScore);

        AppLog.info(scoreObj.toString(), g);
        // IF PERCENTAGE IS USED :
        // scoreObj.put("score", totalScore > 0 ? (okScore*100)/totalScore : 0);
        return scoreObj;

    }

    private boolean isCertif(String id) {

        Grant g = getGrant();
        return (g.simpleQuery("select qual_usrexam_id from qual_user_exam where row_id = " + id)).contains("Certif");
    }

    public static boolean isExamOver(String endDate) {

        if (!"".equals(endDate)) {
            return LocalDate.parse(Tool.getCurrentDate()).isAfter(LocalDate.parse(endDate));
        }
        return false;
    }

    private void createTestElement(String exId) throws ValidateException, CreateException {

        ObjectDB test = getGrant().getTmpObject("QualExUsr");
        test.setFieldValue("qualExusrUsrexamId", getRowId());
        test.setFieldValue("qualExusrExamexId", exId);
        BusinessObjectTool bot = new BusinessObjectTool(test);
        bot.validateAndCreate();

    }

}
