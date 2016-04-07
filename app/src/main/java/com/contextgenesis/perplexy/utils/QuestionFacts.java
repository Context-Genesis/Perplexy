package com.contextgenesis.perplexy.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by bhutanidhruv16 on 28-Mar-16.
 */

public class QuestionFacts {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static void increment_correct(Context context) {
        pref = context.getSharedPreferences(com.contextgenesis.perplexy.utils.Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();
        int correct_count = pref.getInt(com.contextgenesis.perplexy.utils.Constants.CORRECT_COUNT, 0);

        editor.putInt(com.contextgenesis.perplexy.utils.Constants.CORRECT_COUNT, correct_count + 1).apply();

        editor.putFloat(com.contextgenesis.perplexy.utils.Constants.ACCURACY, (float) pref.getInt(com.contextgenesis.perplexy.utils.Constants.CORRECT_COUNT, 0) / ((float) pref.getInt(com.contextgenesis.perplexy.utils.Constants.CORRECT_COUNT, 0) + (float) pref.getInt(com.contextgenesis.perplexy.utils.Constants.INCORRECT_COUNT, 0))).apply();
    }

    public static void increment_incorrect(Context context) {
        pref = context.getSharedPreferences(com.contextgenesis.perplexy.utils.Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();
        int incorrect_count = pref.getInt(com.contextgenesis.perplexy.utils.Constants.INCORRECT_COUNT, 0);

        editor.putInt(com.contextgenesis.perplexy.utils.Constants.INCORRECT_COUNT, incorrect_count + 1).apply();

        editor.putFloat(com.contextgenesis.perplexy.utils.Constants.ACCURACY, ((float) pref.getInt(com.contextgenesis.perplexy.utils.Constants.CORRECT_COUNT, 0) / ((float) pref.getInt(com.contextgenesis.perplexy.utils.Constants.CORRECT_COUNT, 0) + (float) pref.getInt(com.contextgenesis.perplexy.utils.Constants.INCORRECT_COUNT, 0)))).apply();
    }
}
