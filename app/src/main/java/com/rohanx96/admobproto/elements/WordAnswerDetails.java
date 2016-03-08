package com.rohanx96.admobproto.elements;

import android.content.Context;

import com.orm.SugarRecord;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by rish on 8/3/16.
 */
public class WordAnswerDetails extends SugarRecord {

    public int question_id;
    public String status; /*correct, incorrect, unattempted*/
    public boolean hint_display, answer_display;

    public WordAnswerDetails() {

    }

    public WordAnswerDetails(int question_id, String status, boolean hint_display, boolean answer_display) {
        this.question_id = question_id;
        this.status = status;
        this.hint_display = hint_display;
        this.answer_display = answer_display;
    }

    @Override
    public String toString() {
        return "WordAnswerDetails{" +
                "question_id=" + question_id +
                ", status='" + status + '\'' +
                ", hint_display=" + hint_display +
                ", answer_display=" + answer_display +
                '}';
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
        ArrayList<WordQuestion> wordQuestions = JSONUtils.getRiddleQuestionsFromJSONString(context);
        for (int i = 0; i < wordQuestions.size(); i++) {
            int question_id = wordQuestions.get(i).question_id;
            WordAnswerDetails wordAnswersDetails = new WordAnswerDetails(question_id, Constants.UNATTEMPTED, false, false);
            wordAnswersDetails.save();
        }
    }
}