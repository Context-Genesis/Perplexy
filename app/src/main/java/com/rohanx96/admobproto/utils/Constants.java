package com.rohanx96.admobproto.utils;

/**
 * Created by rish on 8/3/16.
 */

public class Constants {

    public static final String VOLUME = "volume";

    public static final String JSON_SEQUENCES_FILE = "sequences";
    public static final String JSON_RIDDLES_FILE = "riddles";
    public static final String JSON_LOGIC_FILE = "logic";

    public static final String JSON_ALL_QUESTIONS_FILE = "all_questions";

    public static final int GAME_TYPE_SEQUENCES = 0;
    public static final int GAME_TYPE_RIDDLE = 1;
    public static final int GAME_TYPE_LOGIC = 2;

    // status

    public static final int CORRECT = 0;
    public static final int INCORRECT = 1;
    public static final int UNAVAILABLE = 2;
    public static final int AVAILABLE = 3;

    public static final String BUNDLE_QUESTION_NUMBER = "BUNDLE_QUESTION_NUMBER";
    public static final String BUNDLE_QUESTION_CATEGORY = "BUNDLE_QUESTION_CATEGORY";
    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    public static final String FIRST_RUN = "FIRST_RUN";

    // Use these strings and integers values for coins
    public static final String PREF_COINS = "Coins";
    public static final String PREF_COINS_EARNED = "Earned";
    public static final String PREF_COINS_SPENT = "Spent";

    public static final String PREF_SHOW_AD = "ShowAd";
    public static final int AD_DISPLAY_LIMIT = 2;

    public static final String PREF_SHOW_RATE_US = "RateUs";

    public static final long INITIAL_COINS = 250;
    public static final int HINT_PRICE = 100;
    public static final int CORRECT_PRICE = 100;
    public static final int INCORRECT_PRICE = 0;
    public static final int SOLUTION_PRICE = 200;
    public static final int UNLOCK_INCORRECT_PRICE = 125;
    public static final int UNLOCK_UNAVAILABLE_PRICE = 150;
    public static final int AD_VALUE_COINS = 75;

    public static final String CORRECT_COUNT = "correctcount";
    public static final String INCORRECT_COUNT = "incorrectcount";
    public static final String ACCURACY = "accuracy";
    public static final String ERROR = null;

    public static final int RIDDLE_COUNT = 50;
    public static final int SEQUENCE_COUNT = 50;
    public static final int LOGIC_QUESTION = 50;
}