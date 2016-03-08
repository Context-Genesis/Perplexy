package com.rohanx96.admobproto.elements;

import com.orm.SugarRecord;

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
}