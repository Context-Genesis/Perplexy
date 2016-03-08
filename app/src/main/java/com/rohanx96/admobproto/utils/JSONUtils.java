package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.util.Log;

import com.rohanx96.admobproto.elements.MCQQuestion;
import com.rohanx96.admobproto.elements.WordQuestion;

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

    public static ArrayList<MCQQuestion> getSequenceQuestionsFromJSONString(Context context) {
        ArrayList<MCQQuestion> MCQQuestions = new ArrayList<>();
        try {
            JSONObject listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE));
            for (int i = 0; i < listObject.getJSONArray("questions").length(); i++) {
                JSONObject questionObj = listObject.getJSONArray("questions").getJSONObject(i);
                int question_id = questionObj.getInt("question_id");
                int answeroption = questionObj.getInt("answeroption");
                String questionstring = questionObj.getString("question");
                String hint = questionObj.getString("hint");
                String message = questionObj.getString("message");
                JSONArray options = questionObj.getJSONArray("options");

                MCQQuestion MCQQuestion = new MCQQuestion(question_id, questionstring, options, answeroption, message, hint);
                MCQQuestions.add(MCQQuestion);
            }
            return MCQQuestions;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getTotalSequenceQuestions(Context context) {
        try {
            JSONObject listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE));
            return listObject.getJSONArray("questions").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static MCQQuestion getSequenceQuestionAt(Context context, int index) {
        JSONObject listObject = null;
        try {
            listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_SEQUENCES_FILE));
            JSONObject questionObj = listObject.getJSONArray("questions").getJSONObject(index);
            int question_id = questionObj.getInt("question_id");
            int answeroption = questionObj.getInt("answeroption");
            String questionstring = questionObj.getString("question");
            String hint = questionObj.getString("hint");
            String message = questionObj.getString("message");
            JSONArray options = questionObj.getJSONArray("options");

            MCQQuestion MCQQuestion = new MCQQuestion(question_id, questionstring, options, answeroption, message, hint);
            return MCQQuestion;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<WordQuestion> getRiddleQuestionsFromJSONString(Context context) {
        ArrayList<WordQuestion> wordQuestions = new ArrayList<>();
        try {
            JSONObject listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE));
            for (int i = 0; i < listObject.getJSONArray("questions").length(); i++) {
                JSONObject questionObj = listObject.getJSONArray("questions").getJSONObject(i);
                int question_id = questionObj.getInt("question_id");
                String answeroptions = questionObj.getString("answeroptions");
                String questionstring = questionObj.getString("question");
                String answer = questionObj.getString("answer");
                String hint = questionObj.getString("hint");
                String message = questionObj.getString("message");

                WordQuestion wordQuestion = new WordQuestion(question_id, questionstring, answer, answeroptions, hint, message);
                wordQuestions.add(wordQuestion);
            }
            return wordQuestions;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getTotalRiddleQuestions(Context context) {
        try {
            JSONObject listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE));
            return listObject.getJSONArray("questions").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static WordQuestion getRiddleQuestionAt(Context context, int index) {
        JSONObject listObject = null;
        try {
            listObject = new JSONObject(loadJSONFromAsset(context, Constants.JSON_RIDDLES_FILE));
            JSONObject questionObj = listObject.getJSONArray("questions").getJSONObject(index);
            int question_id = questionObj.getInt("question_id");
            String answeroptions = questionObj.getString("answeroptions");
            String questionstring = questionObj.getString("question");
            String answer = questionObj.getString("answer");
            String hint = questionObj.getString("hint");
            String message = questionObj.getString("message");

            WordQuestion wordQuestion = new WordQuestion(question_id, questionstring, answer, answeroptions, hint, message);
            return wordQuestion;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}