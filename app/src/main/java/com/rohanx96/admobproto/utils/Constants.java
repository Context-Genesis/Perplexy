package com.rohanx96.admobproto.utils;

/**
 * Created by rish on 8/3/16.
 */
public class Constants {

    public static final String JSON_SEQUENCES_FILE = "sequences.json";
    public static final String JSON_RIDDLES_FILE = "riddles.json";
    public static final String JSON_LOGIC_FILE = "logic.json";

    public static final String JSON_ALL_QUESTIONS_FILE = "all_questions.json";

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

    public static final long INITIAL_COINS = 1500;
    public static final int HINT_PRICE = 75;
    public static final int CORRECT_PRICE = 100;
    public static final int INCORRECT_PRICE = 50;
    public static final int SOLUTION_PRICE = 150;
    public static final int UNLOCK_INCORRECT_PRICE = 100;
    public static final int UNLOCK_UNAVAILABLE_PRICE = 120;

    public static final String ERROR = null;

}