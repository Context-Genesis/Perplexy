package com.rohanx96.admobproto.elements;

import android.content.Context;

import com.orm.SugarRecord;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by rish on 8/3/16.
 */
public class MCQAnswersDetails extends SugarRecord {
    // make getter setters
    // Define state constants here
    public int question_id; // question no , type (int) remove id
    public String status; /*correct, incorrect, available, unavailable (int)*/
    public boolean  lock, //remove lock
            hint_display, answer_display;
    //no_incorrect (int)

    public MCQAnswersDetails() {
    }

    public MCQAnswersDetails(int question_id, String status, boolean lock, boolean hint_display, boolean answer_display) {
        this.question_id = question_id;
        this.status = status;
        this.lock = lock;
        this.hint_display = hint_display;
        this.answer_display = answer_display;
    }

    @Override
    public String toString() {
        return question_id + ", " + status + ", " + lock + ", " + hint_display + ", " + answer_display;
    }

    public static void initializeDatabase(Context context) {
        /*
         *Initialize database with total number of answered questions with default value as that in the JSON question
         * question_id : from JSON File
         * status : UNATTEMPTED
         * lock  : false
         * hint : false
         * answer : false
         */
        ArrayList<MCQQuestion> mMCQQuestions = JSONUtils.getSequenceQuestionsFromJSONString(context);
        for (int i = 0; i < mMCQQuestions.size(); i++) {
            int question_id = mMCQQuestions.get(i).question_id;
            MCQAnswersDetails mMCQAnswersDetail = new MCQAnswersDetails(question_id, Constants.UNATTEMPTED, false, false, false);
            mMCQAnswersDetail.save();
        }
    }
}