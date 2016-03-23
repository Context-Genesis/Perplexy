package com.rohanx96.admobproto.elements;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by rish on 10/3/16.
 */
public class GenericQuestion {

    public int question_number, layout_type, category;
    public String question_name, question, answer, hint, message, explanation, pad_characters;
    public transient JSONArray options;

    public GenericQuestion(int question_number, int layout_type, int category, String question_name, String question, String answer, String hint, String message, String explanation, String pad_characters, JSONArray options) {
        this.question_number = question_number;
        this.layout_type = layout_type;
        this.category = category;
        this.question_name = question_name;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
        this.message = message;
        this.explanation = explanation;
        this.pad_characters = pad_characters;
        this.options = options;
    }

    @Override
    public String toString() {

        /*
        *  Must parse jsonarray to figure out all options. cant simply print JSONArray
         */

        String optionString = "";
        try {
            for (int i = 0; i < options.length(); i++) {
                optionString += options.getJSONObject(i).getString("choice") + ";";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "GenericQuestion{" +
                "questionNumber=" + question_number +
                ", layout_type=" + layout_type +
                ", category='" + category + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", hint='" + hint + '\'' +
                ", message='" + message + '\'' +
                ", explanation='" + explanation + '\'' +
                ", pad_characters='" + pad_characters + '\'' +
                ", options=" + optionString +
                '}';
    }
}