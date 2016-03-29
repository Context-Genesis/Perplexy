package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

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
                mediaPlayer = new MediaPlayer();
            }
            AssetFileDescriptor descriptor = null;
            /* Randomly chose one sound */
            switch (new Random().nextInt(3)) {
                case 0:
                    descriptor = context.getAssets().openFd("sounds/flick_a.ogg");
                    break;
                case 1:
                    descriptor = context.getAssets().openFd("sounds/flick_b.ogg");
                    break;
                case 2:
                    descriptor = context.getAssets().openFd("sounds/flick_c.ogg");
                    break;
            }

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = null;
            /* Randomly chose one sound */
            switch (new Random().nextInt(3)) {
                case 0:
                    descriptor = context.getAssets().openFd("sounds/click_a.ogg");
                    break;
                case 1:
                    descriptor = context.getAssets().openFd("sounds/click_b.ogg");
                    break;
                case 2:
                    descriptor = context.getAssets().openFd("sounds/click_c.ogg");
                    break;
            }

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = null;
            /* Randomly chose one sound */
            switch (new Random().nextInt(3)) {
                case 0:
                    descriptor = context.getAssets().openFd("sounds/click_a.ogg");
                    break;
                case 1:
                    descriptor = context.getAssets().openFd("sounds/click_b.ogg");
                    break;
                case 2:
                    descriptor = context.getAssets().openFd("sounds/click_c.ogg");
                    break;
            }

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = context.getAssets().openFd("sounds/click_c.ogg");

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = context.getAssets().openFd("sounds/click_c.ogg");

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = null;
            descriptor = context.getAssets().openFd("sounds/click_c.ogg");

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = null;
            descriptor = context.getAssets().openFd("sounds/click_c.ogg");

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
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
            AssetFileDescriptor descriptor = null;
            descriptor = context.getAssets().openFd("sounds/click_c.ogg");

            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}