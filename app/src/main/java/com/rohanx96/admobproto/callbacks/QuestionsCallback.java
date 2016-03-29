package com.rohanx96.admobproto.callbacks;

/**
 * Created by rose on 12/3/16.
 */
public interface QuestionsCallback {
    public void toggleIsCharacterOpen();
    public void setIsQuestionLocked(boolean isLocked);
    public int unlockNextQuestion(int category);
    void refreshAdapter();
    public float getCharacterX();
    public float getCharacterY();
    public void showCharacterDialog();
    void setupCharacterDialog();
    public void hideCharacterDialog();
    public void showCharacterUnlockDialog();
    void setupCharacterUnlockDialog();
    public void hideCharacterUnlockDialog();
    void setupCorrectAnswerFeedback(int nextQuestionUnlocked);
    void showCorrectAnswerFeedback(int nextQuestion);
    void hideCorrectAnswerFeedback();
    void setupIncorrectAnswerFeedback();
    void showIncorrectAnswerFeedback();
    void hideIncorrectAnswerFeedback();
}
