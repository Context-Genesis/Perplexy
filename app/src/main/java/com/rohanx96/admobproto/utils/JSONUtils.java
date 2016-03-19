package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.util.Log;

import com.rohanx96.admobproto.elements.GenericQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public String TAG = JSONUtils.this.getClass().getSimpleName();

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

    private static String loadJSONFromAsset(Context context, String WHICH_FILE) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(WHICH_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<GenericQuestion> getQuestionsFromJSONString(Context context, int category) {
        ArrayList<GenericQuestion> genericQuestions = new ArrayList<>();
        try {
            JSONObject listObject = null;
            listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE));

            for (int i = 0; i < listObject.getJSONArray("questions").length(); i++) {
                JSONObject questionObj = listObject.getJSONArray("questions").getJSONObject(i);

                int question_number = questionObj.getInt("question_number");
                int layout_type = questionObj.getInt("layout_type");
                int category_string = questionObj.getInt("category");
                String question_name = questionObj.getString("question_name");
                String question_string = questionObj.getString("question");
                String answer = questionObj.getString("answer");
                String hint = questionObj.getString("hint");
                String message = questionObj.getString("message");
                String explanation = questionObj.getString("explanation");
                String pad_characters = questionObj.getString("pad_characters");
                JSONArray options = questionObj.getJSONArray("options");

                GenericQuestion genericQuestion = new GenericQuestion(question_number, layout_type, category_string, question_name, question_string, answer, hint, message, explanation, pad_characters, options);
                genericQuestions.add(genericQuestion);
            }
            return genericQuestions;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getTotalQuestions(Context context, int category) {
        try {
            JSONObject listObject = null;
            if (category == Constants.GAME_TYPE_RIDDLE)
                listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE));
            else if (category == Constants.GAME_TYPE_SEQUENCES)
                listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE));
            else if (category == Constants.GAME_TYPE_LOGIC)
                listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_LOGIC_FILE));

            if (listObject == null)
                Log.d("JSONUtils", category + " " + listObject);
            else
                Log.d("JSONUtils | ", category + " " + listObject);

            return listObject.getJSONArray("questions").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* The index here is not question number but question position */
    public static GenericQuestion getQuestionAt(Context context, int category, int index) {
        try {
            JSONObject listObject = null;
            if (category == Constants.GAME_TYPE_RIDDLE)
                listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE));
            else if (category == Constants.GAME_TYPE_SEQUENCES)
                listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE));
            else if (category == Constants.GAME_TYPE_LOGIC)
                listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_LOGIC_FILE));

            JSONObject questionObj = listObject.getJSONArray("questions").getJSONObject(index);

            int question_number = questionObj.getInt("question_number");
            int layout_type = questionObj.getInt("layout_type");
            int category_string = questionObj.getInt("category");
            String question_name = questionObj.getString("question_name");
            String question_string = questionObj.getString("question");
            String answer = questionObj.getString("answer");
            String hint = questionObj.getString("hint");
            String message = questionObj.getString("message");
            String explanation = questionObj.getString("explanation");
            String pad_characters = questionObj.getString("pad_characters");
            JSONArray options = questionObj.getJSONArray("options");

            GenericQuestion genericQuestion = new GenericQuestion(question_number, layout_type, category_string, question_name, question_string, answer, hint, message, explanation, pad_characters, options);
            return genericQuestion;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}