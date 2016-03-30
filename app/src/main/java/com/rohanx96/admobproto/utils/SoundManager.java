package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.rohanx96.admobproto.R;

import java.util.Random;

/**
 * Created by rish on 30/3/16.
 */
public class SoundManager {

    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public static void playSwipeSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            /* Randomly chose one sound */
            switch (new Random().nextInt(3)) {
                case 0:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_flick_a);
                    break;
                case 1:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_flick_b);
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_flick_c);
                    break;
            }

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playButtonClickSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }
            /* Randomly chose one sound */
            /* Randomly chose one sound */
            switch (new Random().nextInt(3)) {
                case 0:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_a);
                    break;
                case 1:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_b);
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);
                    break;
            }

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playPadCharacterSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }
            /* Randomly chose one sound */
            /* Randomly chose one sound */
            switch (new Random().nextInt(3)) {
                case 0:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_a);
                    break;
                case 1:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_b);
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);
                    break;
            }

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void playCorrectAnswerSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }

            mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);


            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playWrongAnswerSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }

            mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playCharacterOpenedSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }

            mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playCharacterClosedSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }

            mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBackClickSound(Context context) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
            }

            mediaPlayer = MediaPlayer.create(context, R.raw.sound_click_c);

            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}