package com.rohanx96.admobproto.callbacks;

/**
 * Created by rose on 12/3/16.
 */
public interface QuestionsCallback {
    public void toggleIsCharacterOpen();
    public void setIsQuestionLocked(boolean isLocked);
    public void unlockNextQuestion(int category);
    void refreshAdapter();
    public float getCharacterX();
    public float getCharacterY();
    public void showCharacterDialog();
    public void hideCharacterDialog();
    public void showCharacterUnlockDialog();
    public void hideCharacterUnlockDialog();
}
