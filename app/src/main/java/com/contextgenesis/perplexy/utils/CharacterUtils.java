package com.contextgenesis.perplexy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.contextgenesis.perplexy.ui.CharacterStore;

/**
 * Created by rose on 2/4/16.
 * This class provides implementation for setting character image based on expression and setting character drawable based on character
 * set selected by user.
 */
public class CharacterUtils {

    public static final int EXPRESSION_HAPPY_CLOSED = 0;
    public static final int EXPRESSION_HAPPY_OPEN = 1;
    public static final int EXPRESSION_SAD_CLOSED = 2;
    public static final int EXPRESSION_SAD_OPEN = 3;
    public static final int EXPRESSION_ANGRY = 4;
    public static final int EXPRESSION_SHOCKED = 5;
    public static final int EXPRESSION_BLUSH = 6;

    public static void setCharacterDrawable(Context context, ImageView character, int expression) {
        switch (expression) {
            case EXPRESSION_HAPPY_CLOSED:
                setCharacterHappyClosed(context, character);
                break;
            case EXPRESSION_HAPPY_OPEN:
                setCharacterHappyOpen(context, character);
                break;
            case EXPRESSION_SAD_CLOSED:
                setCharacterSadClosed(context, character);
                break;
            case EXPRESSION_SAD_OPEN:
                setCharacterSadOpen(context, character);
                break;
            case EXPRESSION_ANGRY:
                setCharacterAngry(context, character);
                break;
            case EXPRESSION_SHOCKED:
                setCharacterShocked(context, character);
                break;
            case EXPRESSION_BLUSH:
                setCharacterBlush(context, character);
                break;
        }
    }

    /* In these methods a switch on shared preference that stores user's current character set can be set up to load the particular drawable */

    private static void setCharacterHappyClosed(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_HAPPY_CLOSED, "character_happy_closed_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }

    private static void setCharacterHappyOpen(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_HAPPY_OPEN, "character_happy_open_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }

    private static void setCharacterSadClosed(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_SAD_CLOSED, "character_sad_closed_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }

    private static void setCharacterSadOpen(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_SAD_OPEN, "character_sad_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }

    private static void setCharacterShocked(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_SHOCKED, "character_shocked_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }

    private static void setCharacterAngry(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_ANGRY, "character_angry_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }

    private static void setCharacterBlush(Context context, ImageView character) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String resourceString = prefs.getString(CharacterStore.STRING_EXPRESSION_BLUSH, "character_eyes_closed_128");
        int resourceID = context.getResources().getIdentifier(resourceString, "drawable", context.getPackageName());
        character.setImageResource(resourceID);
    }
}