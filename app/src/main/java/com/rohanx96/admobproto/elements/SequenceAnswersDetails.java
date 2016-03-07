package com.rohanx96.admobproto.elements;

import android.content.Context;

import com.orm.SugarRecord;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rish on 8/3/16.
 */
public class SequenceAnswersDetails extends SugarRecord implements Serializable {

    public int question_id;
    public String status; /*correct, incorrect, unattempted*/
    public boolean lock, hint_display, answer_display;

    public SequenceAnswersDetails() {
    }

    public SequenceAnswersDetails(int question_id, String status, boolean lock, boolean hint_display, boolean answer_display) {
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
        ArrayList<SequenceQuestion> sequenceQuestions = JSONUtils.getSequenceQuestionsFromJSONString(context);
        for (int i = 0; i < sequenceQuestions.size(); i++) {
            int question_id = sequenceQuestions.get(i).question_id;
            SequenceAnswersDetails sequenceAnswersDetails = new SequenceAnswersDetails(question_id, Constants.UNATTEMPTED, false, false, false);
            sequenceAnswersDetails.save();
        }
    }
}