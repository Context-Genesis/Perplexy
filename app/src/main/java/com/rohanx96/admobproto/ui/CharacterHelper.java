package com.rohanx96.admobproto.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.QuestionsCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.elements.GenericQuestion;
import com.rohanx96.admobproto.utils.CharacterStrings;
import com.rohanx96.admobproto.utils.Coins;
import com.rohanx96.admobproto.utils.Constants;
import com.rohanx96.admobproto.utils.JSONUtils;
import com.rohanx96.admobproto.utils.ShareQuestion;
import com.rohanx96.admobproto.utils.SoundManager;

import java.util.ArrayList;

/**
 * Created by rose on 24/3/16.
 */
public class CharacterHelper {
    public static final int CHARACTER_TYPE_UNLOCKED = 0;
    public static final int CHARACTER_TYPE_LOCKED = 1;
    public static final int CHARACTER_TYPE_FEEDBACK_INCORRECT = 2;
    SharedPreferences pref;
    AppCompatActivity mParentActivity;
    TextView coins_display;

    public CharacterHelper(AppCompatActivity mParentActivity) {
        this.mParentActivity = mParentActivity;
        coins_display = (TextView) mParentActivity.findViewById(R.id.questions_activity_coin_text);
    }

    /**
     * This sets up the click listeners for various options in character dialog. Implementation copied from DialogSpeakingMan
     * by Dhruv
     */

    public void setupCharacterDialog(int CATEGORY, final int mCurrentPage, final Context context) {
        final GenericQuestion question = JSONUtils.getQuestionAt(mParentActivity, CATEGORY, mCurrentPage);
        final ArrayList<GenericAnswerDetails> ansDetails = GenericAnswerDetails.listAll(CATEGORY);

        final TextView showhint, hintprice;
        final LinearLayout hint, confirmhint;
        final TextView nohint, yeshint, showhiddenhint, characterText;

        final TextView showsolution, solutionprice;
        final LinearLayout solution, confirmsolution;
        final TextView nosolution, yessolution, showhiddensolution;

        final ImageView favourite = (ImageView) mParentActivity.findViewById(R.id.char_q_clicked_favourite_question);

        showhint = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_showhint);
        hint = (LinearLayout) mParentActivity.findViewById(R.id.char_q_clicked_ll_hint);
        hintprice = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_hintprice);
        confirmhint = (LinearLayout) mParentActivity.findViewById(R.id.char_q_clicked_ll_confirmhint);
        nohint = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_nohint);
        yeshint = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_yeshint);
        showhiddenhint = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_showhiddenhint);
        characterText = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_feedback_text);

        showhint.setVisibility(View.VISIBLE);
        confirmhint.setVisibility(View.GONE);
        showhiddenhint.setVisibility(View.GONE);
        showhiddenhint.setText(question.hint);

        if (ansDetails.get(mCurrentPage).status == Constants.CORRECT) {
            characterText.setText(CharacterStrings.getStringAlreadyAnsweredRight(context));
        } else {
            characterText.setText(CharacterStrings.getStringCharacterTrying(context));
        }

        if (ansDetails.get(mCurrentPage).hint_displayed == true) {
            hintprice.setText("0");
        } else {
            hintprice.setText(Constants.HINT_PRICE + "");
        }

        showhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showhiddenhint.getVisibility() == View.GONE) {
                    showhint.setVisibility(View.GONE);
                    Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                    confirmhint.startAnimation(in);
                    confirmhint.setVisibility(View.VISIBLE);
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        yeshint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmhint.setVisibility(View.GONE);
                showhint.setVisibility(View.VISIBLE);
                pref = mParentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                long coins = pref.getLong(Constants.PREF_COINS, 0);
                if (coins - Integer.parseInt(hintprice.getText().toString()) >= 0) {
                    Animation in = AnimationUtils.loadAnimation(mParentActivity, R.anim.slide_down);
                    showhiddenhint.startAnimation(in);
                    showhiddenhint.setVisibility(View.VISIBLE);

                    if (!ansDetails.get(mCurrentPage).hint_displayed && ansDetails.get(mCurrentPage).status != Constants.CORRECT) {
                        ansDetails.get(mCurrentPage).hint_displayed = true;
                        ansDetails.get(mCurrentPage).save();
                        Coins.hint_access(mParentActivity);
                        coins_display.setText(pref.getLong(Constants.PREF_COINS, 0) + " ");
                    }
                    hintprice.setText("0");
                    SoundManager.playButtonClickSound(context);
                } else {
                    animateAdView(CHARACTER_TYPE_UNLOCKED);
                    Toast.makeText(mParentActivity, "Do not have enough coins",
                            Toast.LENGTH_LONG).show();
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        nohint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmhint.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                showhint.startAnimation(in);
                showhint.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });

        showsolution = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_showsolution);
        solution = (LinearLayout) mParentActivity.findViewById(R.id.char_feedback_incorrect_ll_solution);
        solutionprice = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_solutionprice);
        confirmsolution = (LinearLayout) mParentActivity.findViewById(R.id.char_q_clicked_ll_confirmsolution);
        nosolution = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_nosolution);
        yessolution = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_yessolution);
        showhiddensolution = (TextView) mParentActivity.findViewById(R.id.char_q_clicked_showhiddensolution);

        showsolution.setVisibility(View.VISIBLE);
        confirmsolution.setVisibility(View.GONE);
        showhiddensolution.setVisibility(View.GONE);
        showhiddensolution.setText(question.answer + "\n" + question.explanation);

        if (ansDetails.get(mCurrentPage).answer_displayed == true) {
            solutionprice.setText("0");
        } else {
            solutionprice.setText(Constants.SOLUTION_PRICE + "");
        }

        if (ansDetails.get(mCurrentPage).status == Constants.CORRECT) {
            solutionprice.setText("0");
        }

        showsolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showhiddensolution.getVisibility() == View.GONE) {
                    showsolution.setVisibility(View.GONE);
                    Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                    confirmsolution.startAnimation(in);
                    confirmsolution.setVisibility(View.VISIBLE);
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        yessolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                showsolution.setVisibility(View.VISIBLE);
                pref = mParentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                long coins = pref.getLong(Constants.PREF_COINS, 0);

                if (coins - Integer.parseInt(solutionprice.getText().toString()) >= 0) {
                    Animation in = AnimationUtils.loadAnimation(mParentActivity, R.anim.slide_down);
                    showhiddensolution.startAnimation(in);
                    showhiddensolution.setVisibility(View.VISIBLE);

                    if (!ansDetails.get(mCurrentPage).answer_displayed && ansDetails.get(mCurrentPage).status != Constants.CORRECT) {
                        ansDetails.get(mCurrentPage).answer_displayed = true;
                        ansDetails.get(mCurrentPage).save();
                        Coins.solution_access(mParentActivity);
                        coins_display.setText(pref.getLong(Constants.PREF_COINS, 0) + " ");
                    }
                    solutionprice.setText("0");
                    SoundManager.playButtonClickSound(context);
                } else {
                    animateAdView(CHARACTER_TYPE_UNLOCKED);
                    Toast.makeText(mParentActivity, "Do not have enough coins",
                            Toast.LENGTH_LONG).show();
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        nosolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                showsolution.startAnimation(in);
                showsolution.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });

        if (!ansDetails.get(mCurrentPage).bookmarked) {
            favourite.setImageResource(R.drawable.favorite);  // color
        } else {
            favourite.setImageResource(R.drawable.favourite_filled);
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ansDetails.get(mCurrentPage).bookmarked) {
                    ansDetails.get(mCurrentPage).bookmarked = true;
                    ansDetails.get(mCurrentPage).save();
                    favourite.setImageResource(R.drawable.favourite_filled);  // color
                    SoundManager.playButtonClickSound(context);
                } else {
                    ansDetails.get(mCurrentPage).bookmarked = false;
                    ansDetails.get(mCurrentPage).save();
                    favourite.setImageResource(R.drawable.favorite);
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        mParentActivity.findViewById(R.id.char_q_clicked_whatsapp_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuestionsCallback) mParentActivity).hideCharacterDialog();
                final Handler handler = new Handler();          // delay to give time to dialog to close
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ShareQuestion.shareImageWhatsapp(mParentActivity);
                    }
                }, 600);
                SoundManager.playButtonClickSound(context);
            }
        });

        mParentActivity.findViewById(R.id.char_q_clicked_normal_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuestionsCallback) mParentActivity).hideCharacterDialog();
                final Handler handler = new Handler();          // delay to give time to dialog to close
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShareQuestion.shareIt(mParentActivity, question.question);
                    }
                }, 600);
                SoundManager.playButtonClickSound(context);
            }
        });
    }

    public void setupCharacterUnlockDialog(final int category, final int currentPage, final Context context) {
        int status = GenericAnswerDetails.getStatus(currentPage + 1, category);
        int unlockPriceValue = Constants.UNLOCK_UNAVAILABLE_PRICE;

        TextView feedbackText = (TextView) mParentActivity.findViewById(R.id.char_unlock_feeback_text);

        TextView unlockPrice = (TextView) mParentActivity.findViewById(R.id.char_unlock_price);
        if (status == Constants.INCORRECT) {
            unlockPriceValue = Constants.UNLOCK_INCORRECT_PRICE;
            feedbackText.setText(CharacterStrings.getStringAlreadyAnsweredWrong(context));
        } else if (status == Constants.UNAVAILABLE) {
            unlockPriceValue = Constants.UNLOCK_UNAVAILABLE_PRICE;
            feedbackText.setText(CharacterStrings.getStringQuestionLocked(context));
        }
        unlockPrice.setText(String.format("%d", unlockPriceValue));
        final TextView unlock = (TextView) mParentActivity.findViewById(R.id.char_unlock_tv_unlock);
        final LinearLayout confirmUnlock = (LinearLayout) mParentActivity.findViewById(R.id.char_unlock_ll_confirm_unlock);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlock.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                confirmUnlock.startAnimation(in);
                confirmUnlock.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });

        TextView yesUnlock = (TextView) mParentActivity.findViewById(R.id.char_unlock_yes_unlock);
        final int finalUnlockPriceValue = unlockPriceValue;
        yesUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUnlock.setVisibility(View.GONE);
                unlock.setVisibility(View.VISIBLE);
                pref = mParentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                long coins = pref.getLong(Constants.PREF_COINS, 0);
                if (coins - finalUnlockPriceValue > 0) {
                    GenericAnswerDetails.updateStatus(currentPage + 1, category, Constants.AVAILABLE);
                    Log.i("unlock", "unlocking question");
                    ((QuestionsCallback) mParentActivity).setIsQuestionLocked(false);
                    ((QuestionsCallback) mParentActivity).refreshAdapter();
                    ((QuestionsCallback) mParentActivity).hideCharacterUnlockDialog();
                    if (finalUnlockPriceValue == Constants.UNLOCK_INCORRECT_PRICE)
                        Coins.unlock_incorrect(mParentActivity);
                    else
                        Coins.unlock_unavailable(mParentActivity);
                    coins_display.setText(String.format("%d", coins - finalUnlockPriceValue));
                    SoundManager.playButtonClickSound(context);
                } else {
                    animateAdView(CHARACTER_TYPE_LOCKED);
                    Toast.makeText(mParentActivity, "Do not have enough coins",
                            Toast.LENGTH_LONG).show();
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        TextView noUnlock = (TextView) mParentActivity.findViewById(R.id.char_unlock_no_unlock);
        noUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUnlock.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                unlock.startAnimation(in);
                unlock.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });

    }

    public void setupCorrectAnswerFeedback(int category, int currentPage, final int nextQuestion, final Context context) {
        if (nextQuestion != -1) {
            TextView solution = (TextView) mParentActivity.findViewById(R.id.char_feedback_solution_details);
            GenericQuestion question = JSONUtils.getQuestionAt(mParentActivity, category, currentPage);
            solution.setText(question.answer + "\n" + question.explanation);

            TextView nextLevel = (TextView) mParentActivity.findViewById(R.id.char_feedback_next_question);
            nextLevel.setText(String.format("Question %d is now unlocked", nextQuestion));
            nextLevel.setVisibility(View.VISIBLE);
            TextView coinsEarned = (TextView) mParentActivity.findViewById(R.id.char_feedback_coins_earned);
            coinsEarned.setVisibility(View.VISIBLE);
            TextView congratulate = (TextView) mParentActivity.findViewById(R.id.char_feedback_congratulate);
            congratulate.setVisibility(View.VISIBLE);
            TextView gotoNextLevel = (TextView) mParentActivity.findViewById(R.id.char_feedback_goto_next);
            gotoNextLevel.setText("PROCEED TO UNLOCKED QUESTION");
            gotoNextLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mParentActivity instanceof QuestionsActivity)
                        ((QuestionsActivity) mParentActivity).gotoQuestion(nextQuestion);
                    SoundManager.playButtonClickSound(context);
                }
            });
        } else {
            TextView nextLevel = (TextView) mParentActivity.findViewById(R.id.char_feedback_next_question);
            nextLevel.setVisibility(View.GONE);
            TextView coinsEarned = (TextView) mParentActivity.findViewById(R.id.char_feedback_coins_earned);
            coinsEarned.setVisibility(View.GONE);
            TextView congratulate = (TextView) mParentActivity.findViewById(R.id.char_feedback_congratulate);
            congratulate.setVisibility(View.GONE);
            TextView gotoNextLevel = (TextView) mParentActivity.findViewById(R.id.char_feedback_goto_next);
            gotoNextLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mParentActivity instanceof QuestionsActivity)
                        ((QuestionsActivity) mParentActivity).gotoQuestion(nextQuestion);
                    SoundManager.playButtonClickSound(context);
                }
            });
        }

        TextView feebackTv = (TextView) mParentActivity.findViewById(R.id.char_feedback_title_text);
        feebackTv.setText(CharacterStrings.getStringNowAnsweredRight(context));
    }

    public void setupIncorrectAnswerFeedback(final int category, final int currentPage, final Context context) {
        final GenericQuestion question = JSONUtils.getQuestionAt(mParentActivity, category, currentPage);
        final ArrayList<GenericAnswerDetails> ansDetails = GenericAnswerDetails.listAll(category);

        TextView feebackTv = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_title_text);
        feebackTv.setText(CharacterStrings.getStringNowAnsweredWrong(context));

        TextView unlockPrice = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_unlock_price);
        unlockPrice.setText(String.format("%d", Constants.UNLOCK_INCORRECT_PRICE));
        final TextView unlock = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_tv_unlock);
        final LinearLayout confirmUnlock = (LinearLayout) mParentActivity.findViewById(R.id.char_feedback_incorrect_ll_confirm_unlock);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlock.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                confirmUnlock.startAnimation(in);
                confirmUnlock.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });

        TextView yesUnlock = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_yes_unlock);
        yesUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUnlock.setVisibility(View.GONE);
                unlock.setVisibility(View.VISIBLE);
                pref = mParentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                final long coins = pref.getLong(Constants.PREF_COINS, 0);
                if (coins - Constants.UNLOCK_INCORRECT_PRICE > 0) {
                    GenericAnswerDetails.updateStatus(currentPage + 1, category, Constants.AVAILABLE);
                    Log.i("unlock", "unlocking question");
                    ((QuestionsCallback) mParentActivity).setIsQuestionLocked(false);
                    ((QuestionsCallback) mParentActivity).refreshAdapter();
                    ((QuestionsCallback) mParentActivity).hideIncorrectAnswerFeedback();
                    Coins.unlock_incorrect(mParentActivity);
                    coins_display.setText(String.format("%d", coins - Constants.UNLOCK_INCORRECT_PRICE));
                    SoundManager.playButtonClickSound(context);
                } else {
                    animateAdView(CHARACTER_TYPE_FEEDBACK_INCORRECT);
                    Toast.makeText(mParentActivity, "Do not have enough coins",
                            Toast.LENGTH_LONG).show();
                    SoundManager.playButtonClickSound(context);
                }
            }
        });
        TextView noUnlock = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_no_unlock);
        noUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUnlock.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                unlock.startAnimation(in);
                unlock.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });

        final ImageView favourite = (ImageView) mParentActivity.findViewById(R.id.char_feedback_incorrect_char_q_clicked_favourite_question);

        if (!ansDetails.get(currentPage).bookmarked) {
            favourite.setImageResource(R.drawable.favorite);  // color
        } else {
            favourite.setImageResource(R.drawable.favourite_filled);
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ansDetails.get(currentPage).bookmarked) {
                    ansDetails.get(currentPage).bookmarked = true;
                    ansDetails.get(currentPage).save();
                    favourite.setImageResource(R.drawable.favourite_filled);  // color
                    SoundManager.playButtonClickSound(context);
                } else {
                    ansDetails.get(currentPage).bookmarked = false;
                    ansDetails.get(currentPage).save();
                    favourite.setImageResource(R.drawable.favorite);
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        mParentActivity.findViewById(R.id.char_feedback_incorrect_char_q_clicked_whatsapp_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuestionsCallback) mParentActivity).hideCharacterDialog();
                final Handler handler = new Handler();          // delay to give time to dialog to close
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ShareQuestion.shareImageWhatsapp(mParentActivity);
                    }
                }, 600);
                SoundManager.playButtonClickSound(context);
            }
        });

        mParentActivity.findViewById(R.id.char_feedback_incorrect_char_q_clicked_normal_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuestionsCallback) mParentActivity).hideCharacterDialog();
                final Handler handler = new Handler();          // delay to give time to dialog to close
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShareQuestion.shareIt(mParentActivity, question.question);
                    }
                }, 600);
                SoundManager.playButtonClickSound(context);
            }
        });

        final TextView showsolution = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_showsolution);
        LinearLayout solution = (LinearLayout) mParentActivity.findViewById(R.id.char_feedback_incorrect_ll_solution);
        final TextView solutionprice = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_solutionprice);
        final LinearLayout confirmsolution = (LinearLayout) mParentActivity.findViewById(R.id.char_feedback_incorrect_ll_confirmsolution);
        TextView nosolution = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_nosolution);
        TextView yessolution = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_yessolution);
        final TextView showhiddensolution = (TextView) mParentActivity.findViewById(R.id.char_feedback_incorrect_showhiddensolution);

        showsolution.setVisibility(View.VISIBLE);
        confirmsolution.setVisibility(View.GONE);
        showhiddensolution.setVisibility(View.GONE);

        showhiddensolution.setText(question.answer + "\n" + question.explanation);

        if (ansDetails.get(currentPage).answer_displayed == true) {
            solutionprice.setText("0");
        } else {
            solutionprice.setText(Constants.SOLUTION_PRICE + "");
        }

        showsolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showhiddensolution.getVisibility() == View.GONE) {
                    showsolution.setVisibility(View.GONE);
                    Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                    confirmsolution.startAnimation(in);
                    confirmsolution.setVisibility(View.VISIBLE);
                    SoundManager.playButtonClickSound(context);
                }
            }
        });

        yessolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                showsolution.setVisibility(View.VISIBLE);
                pref = mParentActivity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                long coins = pref.getLong(Constants.PREF_COINS, 0);
                if (coins - Integer.parseInt(solutionprice.getText().toString()) >= 0) {
                    Animation in = AnimationUtils.loadAnimation(mParentActivity, R.anim.slide_down);
                    showhiddensolution.startAnimation(in);
                    showhiddensolution.setVisibility(View.VISIBLE);

                    if (!ansDetails.get(currentPage).answer_displayed) {
                        ansDetails.get(currentPage).answer_displayed = true;
                        ansDetails.get(currentPage).save();
                        Coins.solution_access(mParentActivity);
                        coins_display.setText(pref.getLong(Constants.PREF_COINS, 0) + " ");
                        SoundManager.playButtonClickSound(context);
                    }
                    solutionprice.setText("0");
                } else {
                    animateAdView(CHARACTER_TYPE_FEEDBACK_INCORRECT);
                    Toast.makeText(mParentActivity, "Donot have enough coins",
                            Toast.LENGTH_LONG).show();
                    SoundManager.playButtonClickSound(context);
                }
            }
        });


        nosolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmsolution.setVisibility(View.GONE);
                Animation in = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
                showsolution.startAnimation(in);
                showsolution.setVisibility(View.VISIBLE);
                SoundManager.playButtonClickSound(context);
            }
        });
    }

    public void animateAdView(int type) {
        View adView = null;
        if (type == CHARACTER_TYPE_UNLOCKED)
            adView = mParentActivity.findViewById(R.id.char_q_clicked_ad_video);
        else if (type == CHARACTER_TYPE_LOCKED)
            adView = mParentActivity.findViewById(R.id.char_unlock_clicked_ad_video);
        else if (type == CHARACTER_TYPE_FEEDBACK_INCORRECT)
            adView = mParentActivity.findViewById(R.id.char_feedback_incorrect_ad_video);
        if (adView != null) {
            ObjectAnimator animationUpX = ObjectAnimator.ofFloat(adView, "scaleX", 1.0f, 1.1f);
            ObjectAnimator animatorUpY = ObjectAnimator.ofFloat(adView, "scaleY", 1.0f, 1.1f);
            ObjectAnimator animationDownX = ObjectAnimator.ofFloat(adView, "scaleX", 1.2f, 1.0f);
            ObjectAnimator animatorDownY = ObjectAnimator.ofFloat(adView, "scaleY", 1.2f, 1.0f);
            AnimatorSet set = new AnimatorSet();
            set.play(animationDownX).with(animatorDownY).after(animationUpX).with(animatorUpY);
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).start();
        }
    }
}
