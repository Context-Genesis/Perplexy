package com.contextgenesis.perplexy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contextgenesis.perplexy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharacterStore extends Activity {

    @Bind(R.id.store_back)
    ImageView back;

    @Bind(R.id.store_image)
    ImageView characterImage;

    @Bind(R.id.store_character_name)
    TextView characterName;

    @Bind(R.id.store_lock_image)
    ImageView lockImage;

    @Bind(R.id.store_by_me)
    Button buyButton;

    @Bind(R.id.storeCharacterOption1)
    LinearLayout characterOption1;
    @Bind(R.id.storeCharacterOption2)
    LinearLayout characterOption2;
    @Bind(R.id.storeCharacterOption3)
    LinearLayout characterOption3;
    @Bind(R.id.storeCharacterOption4)
    LinearLayout characterOption4;

    @Bind(R.id.store_horizontal_scroll)
    HorizontalScrollView scrollView;

    @Bind(R.id.store_ll)
    LinearLayout linearLayout;

    @Bind(R.id.store_left)
    ImageView leftButton;
    @Bind(R.id.store_right)
    ImageView rightButton;

    public static final int CHARACTER_GOOD_BWOY = 0;
    public static final int CHARACTER_SUPER_HERO = 1;
    public static final int CHARACTER_BOX_CARTOON = 2;
    public static final int CHARACTER_MINIONS = 3;

    public static final int[] CHARACTER_TYPES = new int[]{CHARACTER_GOOD_BWOY, CHARACTER_SUPER_HERO, CHARACTER_BOX_CARTOON, CHARACTER_MINIONS};

    public static final String CHAR_SHARED_PREFS = "CHAR_SHARED_PREFS";
    public static final String STRING_EXPRESSION_HAPPY_CLOSED = "STRING_EXPRESSION_HAPPY_CLOSED";
    public static final String STRING_EXPRESSION_HAPPY_OPEN = "STRING_EXPRESSION_HAPPY_OPEN";
    public static final String STRING_EXPRESSION_SAD_CLOSED = "STRING_EXPRESSION_SAD_CLOSED";
    public static final String STRING_EXPRESSION_SAD_OPEN = "STRING_EXPRESSION_SAD_OPEN";
    public static final String STRING_EXPRESSION_ANGRY = "STRING_EXPRESSION_ANGRY";
    public static final String STRING_EXPRESSION_SHOCKED = "STRING_EXPRESSION_SHOCKED";
    public static final String STRING_EXPRESSION_BLUSH = "STRING_EXPRESSION_BLUSH";

    public static final String STRING_CURRENT_CHARACTER = "STRING_CURRENT_CHARACTER";
    public static final String STRING_UNLOCKED_CHARACTERS = "STRING_UNLOCKED_CHARACTERS";

    public static final String TAG = CharacterStore.class.getSimpleName();

    private int currentCharacter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_store);
        ButterKnife.bind(this);

        setCharacterNameAndImage(getCurrentCharacter(getApplicationContext()));
        currentCharacter = getCurrentCharacter(getApplicationContext());

    }

    @OnClick(R.id.store_by_me)
    public void buyButton() {
        setUnlockedCharacters(getApplicationContext(), currentCharacter);
        setCharacterNameAndImage(currentCharacter);
        setCurrentCharacter(getApplicationContext(), currentCharacter);
    }

    @OnClick(R.id.storeCharacterOption1)
    public void storeCharacterOption1() {
        setCharacterNameAndImage(CHARACTER_GOOD_BWOY);
    }

    @OnClick(R.id.storeCharacterOption2)
    public void storeCharacterOption2() {
        setCharacterNameAndImage(CHARACTER_SUPER_HERO);
    }

    @OnClick(R.id.storeCharacterOption3)
    public void storeCharacterOption3() {
        setCharacterNameAndImage(CHARACTER_BOX_CARTOON);
    }

    @OnClick(R.id.storeCharacterOption4)
    public void storeCharacterOption4() {
        setCharacterNameAndImage(CHARACTER_MINIONS);
    }

    @OnClick(R.id.store_back)
    public void storeBack() {
        onBackPressed();
    }

    @OnClick(R.id.store_left)
    public void storeLeft() {
        setCharacterNameAndImage(--currentCharacter);
    }

    @OnClick(R.id.store_right)
    public void storeRight() {
        setCharacterNameAndImage(++currentCharacter);
    }

    private void setCharacterNameAndImage(int WHICH) {
        currentCharacter = WHICH;
        removeAllBorders();
        switch (CHARACTER_TYPES[WHICH]) {
            case CHARACTER_GOOD_BWOY:
                characterName.setText("Hi! I'm the Good Bwoy");
                characterImage.setImageResource(R.drawable.character_happy_closed_128);
                characterOption1.setBackgroundResource(R.drawable.transparent_border);
                break;
            case CHARACTER_SUPER_HERO:
                characterName.setText("Hi! I'm za Super Hero");
                characterImage.setImageResource(R.drawable.monster_happy_open);
                characterOption2.setBackgroundResource(R.drawable.transparent_border);
                break;
            case CHARACTER_BOX_CARTOON:
                characterName.setText("Hi! I'm a Box Cartoon");
                characterImage.setImageResource(R.drawable.human_happy_open);
                characterOption3.setBackgroundResource(R.drawable.transparent_border);
                break;
            case CHARACTER_MINIONS:
                characterName.setText("Hi! I'm la Minion");
                characterImage.setImageResource(R.drawable.character_happy_closed_128);
                characterOption4.setBackgroundResource(R.drawable.transparent_border);
                break;
        }

        if (getUnlockedCharacters(getApplicationContext()).charAt(WHICH) == '0') {
            lockImage.setVisibility(View.VISIBLE);
            buyButton.setText("Buy Me!");
        } else {
            buyButton.setText("Use Me!");
            lockImage.setVisibility(View.GONE);
        }

        if (currentCharacter == 0) {
            leftButton.setVisibility(View.GONE);
        } else {
            leftButton.setVisibility(View.VISIBLE);
        }
        if (currentCharacter == CHARACTER_TYPES.length - 1) {
            rightButton.setVisibility(View.GONE);
        } else {
            rightButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateCurrentCharacter(Context context, int WHICH) {
        SharedPreferences prefs = context.getSharedPreferences(CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        switch (WHICH) {
            case CHARACTER_GOOD_BWOY:
                editor.putString(STRING_EXPRESSION_HAPPY_CLOSED, "character_happy_closed_128");
                editor.putString(STRING_EXPRESSION_HAPPY_OPEN, "character_happy_open_128");
                editor.putString(STRING_EXPRESSION_ANGRY, "character_angry_128");
                editor.putString(STRING_EXPRESSION_BLUSH, "character_eyes_closed_128");
                editor.putString(STRING_EXPRESSION_SAD_CLOSED, "character_sad_closed_128");
                editor.putString(STRING_EXPRESSION_SAD_OPEN, "character_sad_128");
                editor.putString(STRING_EXPRESSION_SHOCKED, "character_shocked_128");
                editor.apply();
                break;
            case CHARACTER_SUPER_HERO:
                editor.putString(STRING_EXPRESSION_HAPPY_CLOSED, "monster_angry");
                editor.putString(STRING_EXPRESSION_HAPPY_OPEN, "monster_eyes_closed");
                editor.putString(STRING_EXPRESSION_ANGRY, "monster_happy_closed");
                editor.putString(STRING_EXPRESSION_BLUSH, "monster_happy_open");
                editor.putString(STRING_EXPRESSION_SAD_CLOSED, "monster_sad");
                editor.putString(STRING_EXPRESSION_SAD_OPEN, "monster_sad_closed");
                editor.putString(STRING_EXPRESSION_SHOCKED, "monster_shocked");
                editor.apply();
                break;
            case CHARACTER_BOX_CARTOON:
                editor.putString(STRING_EXPRESSION_HAPPY_CLOSED, "human_angry");
                editor.putString(STRING_EXPRESSION_HAPPY_OPEN, "human_eyes_closed");
                editor.putString(STRING_EXPRESSION_ANGRY, "human_happy_closed");
                editor.putString(STRING_EXPRESSION_BLUSH, "human_happy_open");
                editor.putString(STRING_EXPRESSION_SAD_CLOSED, "human_sad");
                editor.putString(STRING_EXPRESSION_SAD_OPEN, "human_sad_closed");
                editor.putString(STRING_EXPRESSION_SHOCKED, "human_shocked");
                editor.apply();
                break;
            case CHARACTER_MINIONS:
                editor.putString(STRING_EXPRESSION_HAPPY_CLOSED, "character_happy_closed_128");
                editor.putString(STRING_EXPRESSION_HAPPY_OPEN, "character_happy_open_128");
                editor.putString(STRING_EXPRESSION_ANGRY, "character_angry_128");
                editor.putString(STRING_EXPRESSION_BLUSH, "character_eyes_closed_128");
                editor.putString(STRING_EXPRESSION_SAD_CLOSED, "character_sad_closed_128");
                editor.putString(STRING_EXPRESSION_SAD_OPEN, "character_sad_128");
                editor.putString(STRING_EXPRESSION_SHOCKED, "character_shocked_128");
                editor.apply();
                break;
        }
    }

    private void setCurrentCharacter(Context context, int WHICH) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        prefs.edit().putInt(STRING_CURRENT_CHARACTER, WHICH).apply();
        updateCurrentCharacter(getApplicationContext(), WHICH);
    }

    private int getCurrentCharacter(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(STRING_CURRENT_CHARACTER, 0);
    }

    private void setUnlockedCharacters(Context context, int WHICH) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        String booleanArray = getUnlockedCharacters(context);
        StringBuilder builder = new StringBuilder(booleanArray);
        builder.setCharAt(WHICH, '1');
        prefs.edit().putString(STRING_UNLOCKED_CHARACTERS, builder.toString()).apply();
    }

    private String getUnlockedCharacters(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CharacterStore.CHAR_SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getString(STRING_UNLOCKED_CHARACTERS, "1000");
        /*SET 1000 by default for the first character is unlocked by default*/
    }

    private void removeAllBorders() {
        characterOption1.setBackgroundResource(0);
        characterOption2.setBackgroundResource(0);
        characterOption3.setBackgroundResource(0);
        characterOption4.setBackgroundResource(0);
    }
}