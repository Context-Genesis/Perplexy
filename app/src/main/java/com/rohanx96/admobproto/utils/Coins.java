package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by bhutanidhruv16 on 12-Mar-16.
 */

public class Coins {
    // TODO: Coins can go negative?
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static long getCurrentCoins(Context context){
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return pref.getLong(Constants.PREF_COINS, 0);
    }

    public static void hint_access(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        long coins = pref.getLong(Constants.PREF_COINS, 0);

        if (coins >= Constants.HINT_PRICE) {
            editor.putLong(Constants.PREF_COINS, coins - Constants.HINT_PRICE).apply();
            long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
            editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.HINT_PRICE).apply();
        } else {
            Toast.makeText(context, "Do not have sufficient coins", Toast.LENGTH_SHORT).show();
        }
    }

    public static void solution_access(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        long coins = pref.getLong(Constants.PREF_COINS, 0);

        if (coins >= Constants.SOLUTION_PRICE) {
            editor.putLong(Constants.PREF_COINS, coins - Constants.SOLUTION_PRICE).apply();
            long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
            editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.SOLUTION_PRICE).apply();
        } else {
            Toast.makeText(context, "Do not have sufficient coins", Toast.LENGTH_SHORT).show();
        }

    }

    public static void unlock_incorrect(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);

        if (coins >= Constants.UNLOCK_INCORRECT_PRICE) {
            editor.putLong(Constants.PREF_COINS, coins - Constants.UNLOCK_INCORRECT_PRICE).apply();
            long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
            editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.UNLOCK_INCORRECT_PRICE).apply();
        } else {
            Toast.makeText(context, "Do not have sufficient coins", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Test when coins = unlock price
    public static void unlock_unavailable(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);
        if (coins >= Constants.UNLOCK_UNAVAILABLE_PRICE) {
            editor.putLong(Constants.PREF_COINS, coins - Constants.UNLOCK_UNAVAILABLE_PRICE).apply();
            long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
            editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.UNLOCK_UNAVAILABLE_PRICE).apply();
        } else {
            Toast.makeText(context, "Do not have sufficient coins", Toast.LENGTH_SHORT).show();
        }
    }

    public static void correct_answer(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);
        editor.putLong(Constants.PREF_COINS, coins + Constants.CORRECT_PRICE).apply();

        long earned_coins = pref.getLong(Constants.PREF_COINS_EARNED, 0);
        editor.putLong(Constants.PREF_COINS_EARNED, earned_coins + Constants.CORRECT_PRICE).apply();

        QuestionFacts.increment_correct(context);
    }

    public static void wrong_answer(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);
        editor.putLong(Constants.PREF_COINS, coins - Constants.INCORRECT_PRICE).apply();

        QuestionFacts.increment_incorrect(context);
    }

    public static void addCoinsFromAd(Context context){
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);
        editor.putLong(Constants.PREF_COINS, coins + Constants.AD_VALUE_COINS).apply();

        long earned_coins = pref.getLong(Constants.PREF_COINS_EARNED, 0);

        editor.putLong(Constants.PREF_COINS_EARNED, earned_coins + Constants.AD_VALUE_COINS).apply();
    }
}