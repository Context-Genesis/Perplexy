package com.rohanx96.admobproto.utils;

import android.content.Context;
import android.util.Log;

import com.rohanx96.admobproto.elements.SequenceQuestion;

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

    public static ArrayList<SequenceQuestion> getSequenceQuestionsFromJSONString(Context context) {
        ArrayList<SequenceQuestion> sequenceQuestions = new ArrayList<>();
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

                SequenceQuestion sequenceQuestion = new SequenceQuestion(question_id, questionstring, options, answeroption, message, hint);
                sequenceQuestions.add(sequenceQuestion);
            }
            return sequenceQuestions;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}