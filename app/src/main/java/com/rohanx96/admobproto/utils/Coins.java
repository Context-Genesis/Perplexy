package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bhutanidhruv16 on 12-Mar-16.
 */
public class Coins {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static void hint_access(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        long coins = pref.getLong(Constants.PREF_COINS, 0);

        editor.putLong(Constants.PREF_COINS, coins - Constants.HINT_PRICE).apply();
        long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
        editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.HINT_PRICE).apply();
    }

    public static void solution_access(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        long coins = pref.getLong(Constants.PREF_COINS, 0);

        editor.putLong(Constants.PREF_COINS, coins - Constants.SOLUTION_PRICE).apply();
        long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
        editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.SOLUTION_PRICE).apply();

    }

    // TODO: deduct coins.. check if sufficient coins
    public static void unlock_incorrect(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);
        editor.putLong(Constants.PREF_COINS, coins - Constants.UNLOCK_INCORRECT_PRICE).apply();
        long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
        editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.UNLOCK_INCORRECT_PRICE).apply();
    }

    // TODO: deduct coins .. check if sufficient coins
    public static void unlock_unavailable(Context context) {
        pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, context.MODE_PRIVATE);
        editor = pref.edit();

        long coins = pref.getLong(Constants.PREF_COINS, 0);
        editor.putLong(Constants.PREF_COINS, coins - Constants.UNLOCK_UNAVAILABLE_PRICE).apply();
        long spent_coins = pref.getLong(Constants.PREF_COINS_SPENT, 0);
        editor.putLong(Constants.PREF_COINS_SPENT, spent_coins + Constants.UNLOCK_UNAVAILABLE_PRICE).apply();
    }
}