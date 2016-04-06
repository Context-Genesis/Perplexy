package com.rohanx96.admobproto.utils;

import android.content.Context;

import com.rohanx96.admobproto.R;

import java.util.Random;

/**
 * Created by rish on 3/4/16.
 */
public class CharacterStrings {

    public static String getStringCharacterTrying(Context context) {
        String charText1 = context.getString(R.string.characterTrying1);
        String charText2 = context.getString(R.string.characterTrying2);
        String charText3 = context.getString(R.string.characterTrying3);
        String charText4 = context.getString(R.string.characterTrying4);

        switch (new Random().nextInt(4)) {
            case 0:
                return charText1;
            case 1:
                return charText2;
            case 2:
                return charText3;
            case 3:
                return charText4;
            default:
                return charText1;
        }
    }

    public static String getStringAlreadyAnsweredRight(Context context) {
        String charText1 = context.getString(R.string.characterAlreadyAnsweredRight1);
        String charText2 = context.getString(R.string.characterAlreadyAnsweredRight2);
        String charText3 = context.getString(R.string.characterAlreadyAnsweredRight3);
        String charText4 = context.getString(R.string.characterAlreadyAnsweredRight4);

        switch (new Random().nextInt(4)) {
            case 0:
                return charText1;
            case 1:
                return charText2;
            case 2:
                return charText3;
            case 3:
                return charText4;
            default:
                return charText1;
        }
    }

    public static String getStringAlreadyAnsweredWrong(Context context) {
        String charText1 = context.getString(R.string.characterAlreadyAnsweredWrong1);
        String charText2 = context.getString(R.string.characterAlreadyAnsweredWrong2);
        String charText3 = context.getString(R.string.characterAlreadyAnsweredWrong3);
        String charText4 = context.getString(R.string.characterAlreadyAnsweredWrong4);

        switch (new Random().nextInt(4)) {
            case 0:
                return charText1;
            case 1:
                return charText2;
            case 2:
                return charText3;
            case 3:
                return charText4;
            default:
                return charText1;
        }
    }

    public static String getStringNowAnsweredRight(Context context) {
        String charText1 = context.getString(R.string.characterAnsweredNowRight1);
        String charText2 = context.getString(R.string.characterAnsweredNowRight2);
        String charText3 = context.getString(R.string.characterAnsweredNowRight3);

        switch (new Random().nextInt(3)) {
            case 0:
                return charText1;
            case 1:
                return charText2;
            case 2:
                return charText3;
            default:
                return charText1;
        }
    }

    public static String getStringNowAnsweredWrong(Context context) {
        String charText1 = context.getString(R.string.characterAnsweredNowWrong1);
        String charText2 = context.getString(R.string.characterAnsweredNowWrong2);
        String charText3 = context.getString(R.string.characterAnsweredNowWrong3);

        switch (new Random().nextInt(3)) {
            case 0:
                return charText1;
            case 1:
                return charText2;
            case 2:
                return charText3;
            default:
                return charText1;
        }
    }

    public static String getStringQuestionLocked(Context context) {
        String charText1 = context.getString(R.string.characterQuestionLocked1);
        String charText2 = context.getString(R.string.characterQuestionLocked2);
        String charText3 = context.getString(R.string.characterQuestionLocked3);
        String charText4 = context.getString(R.string.characterQuestionLocked4);
        String charText5 = context.getString(R.string.characterQuestionLocked5);
        String charText6 = context.getString(R.string.characterQuestionLocked6);
        String charText7 = context.getString(R.string.characterQuestionLocked7);

        switch (new Random().nextInt(7)) {
            case 0:
                return charText1;
            case 1:
                return charText2;
            case 2:
                return charText3;
            case 3:
                return charText4;
            case 4:
                return charText5;
            case 5:
                return charText6;
            case 6:
                return charText7;
            default:
                return charText1;
        }
    }
}