package com.rohanx96.admobproto.elements;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;

import java.util.ArrayList;

/**
 * Created by rish on 10/3/16.
 */
public class GenericAnswerDetails extends SugarRecord {

    public int question_number;
    public int category, status; /*correct, incorrect, available, unavailable (int)*/
    public boolean hint_displayed, answer_displayed;
    public int number_incorrect;

    public GenericAnswerDetails() {

    }

    public GenericAnswerDetails(int question_number, int category, int status, boolean hint_displayed, boolean answer_displayed, int number_incorrect) {
        this.question_number = question_number;
        this.category = category;
        this.status = status;
        this.hint_displayed = hint_displayed;
        this.answer_displayed = answer_displayed;
        this.number_incorrect = number_incorrect;
    }

    @Override
    public String toString() {
        return "GenericAnswerDetails{" +
                "question_number=" + question_number +
                ", status='" + status + '\'' +
                ", hint_displayed=" + hint_displayed +
                ", answer_displayed=" + answer_displayed +
                ", number_incorrect=" + number_incorrect +
                '}';
    }

    public static void initializeDatabase(Context context) {
        /*
         *Initialize database with total number of answered questions with default value as that in the JSON question
         * question_id : from JSON File
         * status : UNAVAILABLE
         * hint : false
         * answer : false
         * number_incorrect : 0
         */

        ArrayList<GenericQuestion> allQuestions = new ArrayList<>();
        allQuestions.addAll(JSONUtils.getQuestionsFromJSONString(context, Constants.GAME_TYPE_RIDDLE));
        allQuestions.addAll(JSONUtils.getQuestionsFromJSONString(context, Constants.GAME_TYPE_SEQUENCES));
        /* allQuestions.addAll(JSONUtils.getQuestionsFromJSONString(context, Constants.GAME_TYPE_LOGIC));*/

        for (int i = 0; i < allQuestions.size(); i++) {
            int question_number = allQuestions.get(i).question_number;
            int category = allQuestions.get(i).category;
            GenericAnswerDetails genericAnswerDetails;

            // Initialise first three questions as available. TODO: unlock for each category
            if (i<3)
                genericAnswerDetails = new GenericAnswerDetails(question_number, category, Constants.AVAILABLE, false, false, 0);
            else
                genericAnswerDetails = new GenericAnswerDetails(question_number, category, Constants.UNAVAILABLE, false, false, 0);
            genericAnswerDetails.save();
        }
    }

    public static ArrayList<GenericAnswerDetails> listAll(int category) {
        return (ArrayList<GenericAnswerDetails>) Select.from(GenericAnswerDetails.class).where(Condition.prop("category").eq(category)).list();
    }

    /*
    public void incrementNumberOfIncorrect(int question_number, String category) {
        this.number_incorrect += 1;
        this.save();
    }
    */
}