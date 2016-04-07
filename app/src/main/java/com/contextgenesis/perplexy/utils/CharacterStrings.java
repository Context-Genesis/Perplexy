package com.contextgenesis.perplexy.utils;

import android.content.Context;

import com.contextgenesis.perplexy.R;

import java.util.Random;

/**
 * Created by rish on 3/4/16.
 */
public class CharacterStrings {

    public static String getStringCharacterTrying(Context context) {
        String charText[] = context.getResources().getStringArray(R.array.characterTrying);
        return charText[new Random().nextInt(charText.length)];
    }

    public static String getStringAlreadyAnsweredRight(Context context) {
        String charText[] = context.getResources().getStringArray(R.array.characterAlreadyAnsweredRight);
        return charText[new Random().nextInt(charText.length)];
    }

    public static String getStringAlreadyAnsweredWrong(Context context) {

        String charText[] = context.getResources().getStringArray(R.array.characterAlreadyAnsweredWrong);
        return charText[new Random().nextInt(charText.length)];

    }

    public static String getStringNowAnsweredRight(Context context) {

        String charText[] = context.getResources().getStringArray(R.array.characterAnsweredNowRight);
        return charText[new Random().nextInt(charText.length)];

    }

    public static String getStringNowAnsweredWrong(Context context) {


        String charText[] = context.getResources().getStringArray(R.array.characterAnsweredNowWrong);
        return charText[new Random().nextInt(charText.length)];

    }

    public static String getStringAlreadyAnsweredRightButWrongNow(Context context) {

        String charText[] = context.getResources().getStringArray(R.array.characterAlreadyAnsweredRightButWrongNow);
        return charText[new Random().nextInt(charText.length)];
    }

    public static String getStringQuestionLocked(Context context) {

        String charText[] = context.getResources().getStringArray(R.array.characterQuestionLocked);
        return charText[new Random().nextInt(charText.length)];

    }
}