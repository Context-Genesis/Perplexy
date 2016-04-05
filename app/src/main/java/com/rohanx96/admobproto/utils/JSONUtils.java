package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.rohanx96.admobproto.elements.GenericQuestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by rish on 8/3/16.
 */
public class JSONUtils {

    public static String TAG = JSONUtils.class.getClass().getSimpleName();

    private static QuestionArray cachedQuestionArray_LOGIC = null;
    private static QuestionArray cachedQuestionArray_RIDDLES = null;
    private static QuestionArray cachedQuestionArray_SEQUENCES = null;

    public boolean writeToFile(String fileName, String fileContent) {
        try {
            File file = new File(BasePath.getBasePath(), fileName);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent);
            bw.close();

            Log.d(TAG, "Success");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readFromFile(String fileName) {

        BufferedReader br = null;
        String response = null;

        try {
            StringBuffer output = new StringBuffer();

            File filePath = new File(BasePath.getBasePath(), fileName);

            br = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line + "\n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private static QuestionArray loadJSONFromAsset(Context context, String WHICH_FILE) {
        QuestionArray questionArray = null;
        try {
            InputStream is = context.getResources().openRawResource(context.getResources().getIdentifier(WHICH_FILE, "raw", context.getPackageName()));
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            questionArray = LoganSquare.parse(json, QuestionArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionArray;

    }

    public static ArrayList<GenericQuestion> getQuestionsFromJSONString(Context context, int category) {
        ArrayList<GenericQuestion> genericQuestions = new ArrayList<>();
        QuestionArray questionArray = null;
        switch (category) {
            case Constants.GAME_TYPE_LOGIC:
                if (cachedQuestionArray_LOGIC == null)
                    cachedQuestionArray_LOGIC = loadJSONFromAsset(context, Constants.JSON_LOGIC_FILE);
                questionArray = cachedQuestionArray_LOGIC;
                break;
            case Constants.GAME_TYPE_SEQUENCES:
                if (cachedQuestionArray_SEQUENCES == null)
                    cachedQuestionArray_SEQUENCES = loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE);
                questionArray = cachedQuestionArray_SEQUENCES;
                break;
            case Constants.GAME_TYPE_RIDDLE:
                if (cachedQuestionArray_RIDDLES == null)
                    cachedQuestionArray_RIDDLES = loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE);
                questionArray = cachedQuestionArray_RIDDLES;
                break;
            default:
                Log.wtf(TAG, "ERROR IN getQuestionsFromJSONString");
                break;
        }

        for (GenericQuestion question : questionArray.questionsArray) {
            GenericQuestion genericQuestion = new GenericQuestion(question.question_number, question.layout_type, question.category, question.question_name, question.question, question.answer, question.hint, question.message, question.explanation, question.pad_characters, question.options);
            genericQuestions.add(genericQuestion);
        }
        Log.i("questions total ", genericQuestions.size() + "");
        return genericQuestions;
    }

    public static int getTotalQuestions(Context context, int category) {
        QuestionArray questionArray = null;
        switch (category) {
            case Constants.GAME_TYPE_LOGIC:
                if (cachedQuestionArray_LOGIC == null)
                    cachedQuestionArray_LOGIC = loadJSONFromAsset(context, Constants.JSON_LOGIC_FILE);
                questionArray = cachedQuestionArray_LOGIC;
                break;
            case Constants.GAME_TYPE_SEQUENCES:
                if (cachedQuestionArray_SEQUENCES == null)
                    cachedQuestionArray_SEQUENCES = loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE);
                questionArray = cachedQuestionArray_SEQUENCES;
                break;
            case Constants.GAME_TYPE_RIDDLE:
                if (cachedQuestionArray_RIDDLES == null)
                    cachedQuestionArray_RIDDLES = loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE);
                questionArray = cachedQuestionArray_RIDDLES;
                break;
            default:
                Log.wtf(TAG, "ERROR IN getQuestionsFromJSONString");
                break;
        }
        return questionArray.questionsArray.size();
    }

    /* The index here is not question number but question position */
    public static GenericQuestion getQuestionAt(Context context, int category, int index) {
        QuestionArray questionArray = null;
        switch (category) {
            case Constants.GAME_TYPE_LOGIC:
                if (cachedQuestionArray_LOGIC == null)
                    cachedQuestionArray_LOGIC = loadJSONFromAsset(context, Constants.JSON_LOGIC_FILE);
                questionArray = cachedQuestionArray_LOGIC;
                break;
            case Constants.GAME_TYPE_SEQUENCES:
                if (cachedQuestionArray_SEQUENCES == null)
                    cachedQuestionArray_SEQUENCES = loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE);
                questionArray = cachedQuestionArray_SEQUENCES;
                break;
            case Constants.GAME_TYPE_RIDDLE:
                if (cachedQuestionArray_RIDDLES == null)
                    cachedQuestionArray_RIDDLES = loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE);
                questionArray = cachedQuestionArray_RIDDLES;
                break;
            default:
                Log.wtf(TAG, "ERROR IN getQuestionsFromJSONString");
                break;
        }
        GenericQuestion question = questionArray.questionsArray.get(index);
        GenericQuestion genericQuestion = new GenericQuestion(question.question_number, question.layout_type, question.category, question.question_name, question.question, question.answer, question.hint, question.message, question.explanation, question.pad_characters, question.options);
        return genericQuestion;
    }
}