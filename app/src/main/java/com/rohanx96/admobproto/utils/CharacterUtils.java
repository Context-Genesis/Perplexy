package com.rohanx96.admobproto.utils;

import android.widget.ImageView;

import com.rohanx96.admobproto.R;

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

    public static void setCharacterDrawable(ImageView character, int expression) {
        switch (expression) {
            case EXPRESSION_HAPPY_CLOSED:
                setCharacterHappyClosed(character);
                break;
            case EXPRESSION_HAPPY_OPEN:
                setCharacterHappyOpen(character);
                break;
            case EXPRESSION_SAD_CLOSED:
                setCharacterSadClosed(character);
                break;
            case EXPRESSION_SAD_OPEN:
                setCharacterSadOpen(character);
                break;
            case EXPRESSION_ANGRY:
                setCharacterAngry(character);
                break;
            case EXPRESSION_SHOCKED:
                setCharacterShocked(character);
                break;
            case EXPRESSION_BLUSH:
                setCharacterBlush(character);
                break;
        }
    }

    /* In these methods a switch on shared preference that stores user's current character set can be set up to load the particular drawable */

    private static void setCharacterHappyClosed(ImageView character) {
        character.setImageResource(R.drawable.character_happy_closed_128);
    }

    private static void setCharacterHappyOpen(ImageView character) {
        character.setImageResource(R.drawable.character_happy_open_128);
    }

    private static void setCharacterSadClosed(ImageView character) {
        character.setImageResource(R.drawable.character_sad_closed_128);
    }

    private static void setCharacterSadOpen(ImageView character) {
        character.setImageResource(R.drawable.character_sad_128);
    }

    private static void setCharacterShocked(ImageView character) {
        character.setImageResource(R.drawable.character_shocked_128);
    }

    private static void setCharacterAngry(ImageView character) {
        character.setImageResource(R.drawable.character_angry_128);
    }

    private static void setCharacterBlush(ImageView character) {
        character.setImageResource(R.drawable.character_eyes_closed_128);
    }
}
