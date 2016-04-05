package com.rohanx96.admobproto.utils;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rohanx96.admobproto.elements.GenericQuestion;

import java.util.List;

/**
 * Created by rish on 5/4/16.
 */

@JsonObject
public class QuestionArray {

    @JsonField(name = "questions")
    public List<GenericQuestion> questionsArray;

}
