package com.simplicite.extobjects.Qualification;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

import org.json.*;

/**
 * External object QualStartTestExt
 */
public class QualStartTestExt extends ExternalObject {

    private static final long serialVersionUID = 1L;

    /**
     * Display method
     * 
     * @param params
     *            Request parameters
     */
    @Override
    public Object display(Parameters params) {

        try {

            Grant g = getGrant();

            setDecoration(false);
            appendMustache();
            appendCSSInclude(HTMLTool.getResourceCSSURL(this, "STYLES"));
            appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
            appendJSIncludes(HTMLTool.bootboxJS());

            JSONObject vals = new JSONObject();

            JSONArray exams = new JSONArray();

            ObjectDB exam = g.getTmpObject("QualUserExam");

            exam.resetValues();
            exam.setFieldFilter("qualUsrexamUsrId", g.getUserId());

            List<String[]> rslts = exam.search();

            for (String[] row : rslts) {

                exam.setValues(row, false);
                JSONObject examData = new JSONObject();
                examData.put("id", exam.getRowId());
                examData.put("name", exam.getFieldValue("qualUsrexamExamId.qualExamName"));
                examData.put("state", exam.getFieldDisplayValue("qualUsrexamEtat"));
                examData.put("dateLimite", exam.getFieldDisplayValue("qualUsrexamDateLimite"));
                examData.put("complete-class",
                        "TODO".equals(exam.getFieldValue("qualUsrexamEtat")) ? "qual-incomplete" : "qual-complete");
                exams.put(examData);
            }

            vals.put("exams", exams);

            AppLog.info(getClass(), "vals", vals.toString(), getGrant());
            return sendJavaScript("QualStartTestExt.render('" + vals.toString() + "');");

        } catch (Exception e) {
            AppLog.error(getClass(), "display", null, e, getGrant());
            return e.getMessage();
        }
    }
}
