package com.rohanx96.admobproto.elements;

/**
 * Created by rish on 8/3/16.
 */
public class WordQuestion {

    public int question_id;
    public String question, hint, message, answer, answerOptions;

    public WordQuestion(int question_id, String question, String answer, String answerOptions, String hint, String message) {
        this.question_id = question_id;
        this.question = question;
        this.hint = hint;
        this.message = message;
        this.answer = answer;
        this.answerOptions = answerOptions;
    }

    @Override
    public String toString() {
        return "WordQuestion{" +
                "question_id=" + question_id +
                ", question='" + question + '\'' +
                ", hint='" + hint + '\'' +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                ", answerOptions='" + answerOptions + '\'' +
                '}';
    }
}
