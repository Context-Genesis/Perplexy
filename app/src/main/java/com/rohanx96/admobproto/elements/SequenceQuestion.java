package com.rohanx96.admobproto.elements;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by rish on 8/3/16.
 */
public class SequenceQuestion implements Serializable {

    public int question_id, answeroption;
    public String question, hint, message;
    public transient JSONArray options;

    public SequenceQuestion(int question_id, String question, JSONArray options, int answeroption, String message, String hint) {
        this.question_id = question_id;
        this.answeroption = answeroption;
        this.message = message;
        this.question = question;
        this.hint = hint;
        this.options = options;
    }

    @Override
    public String toString() {
        String optionString = "";
        try {
            for (int i = 0; i < options.length(); i++) {
                optionString += options.getJSONObject(i).getString("choice") + ";";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return question_id + "," + answeroption + ", " + message + ", " + question + ", " + hint + ", " + optionString;
    }
}