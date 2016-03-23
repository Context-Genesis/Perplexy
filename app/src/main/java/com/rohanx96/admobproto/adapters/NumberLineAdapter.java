package com.rohanx96.admobproto.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.callbacks.NumberLineCallback;
import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.ui.NumberLineActivity;
import com.rohanx96.admobproto.ui.QuestionsActivity;
import com.rohanx96.admobproto.utils.Constants;

import java.util.ArrayList;

/**
 * Created by bhutanidhruv16 on 07-Mar-16.
 */

public class NumberLineAdapter extends BaseAdapter {

    NumberLineActivity context;
    ArrayList<GenericAnswerDetails> answerDetails;
    LayoutInflater inflater;
    NumberLineCallback numberLineCallback;

    public NumberLineAdapter(NumberLineActivity context, ArrayList<GenericAnswerDetails> answerDetails, NumberLineCallback numberLineCallback) {
        this.context = context;
        this.answerDetails = answerDetails;
        this.numberLineCallback = numberLineCallback;
        /*
        * Add for padding of the arrows on the number line
        */
        this.answerDetails.add(0, new GenericAnswerDetails(-1, Constants.CORRECT, Constants.UNAVAILABLE, false, false, 0));
        this.answerDetails.add(answerDetails.size(), new GenericAnswerDetails(-1, Constants.CORRECT, Constants.UNAVAILABLE, false, false, 0));
    }


    @Override
    public int getCount() {
        return answerDetails.size();
    }

    @Override
    public GenericAnswerDetails getItem(int position) {
        return answerDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        View vi = convertView;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            if (type == 0) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line_left, null);
                holder.tv = (Button) vi.findViewById(R.id.element_number_line_left_textview);
            } else if (type == 1) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line_right, null);
                holder.tv = (Button) vi.findViewById(R.id.element_number_line_right_textview);
            } else if (type == -1) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line_start, null);
                holder.tv = (Button) vi.findViewById(R.id.element_number_line_start_uparrow);
            } else {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line_end, null);
                holder.tv = (Button) vi.findViewById(R.id.element_number_line_end_downarrow);
            }

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        /*
        * Process answer details here and set backgrounds and lock image accordingly
        */

        // Text and click listeners are not set for number line arrows
        if (position != 0 && position != answerDetails.size() - 1) {
            holder.tv.setText(position + "");
            // Set background drawable for question number based on status
            setBackgroundDrawable(position, holder);
            setOnClickListeners(position, holder);
        }

        return vi;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return -1;
        else if (position == answerDetails.size() - 1)
            return -2;

        return position % 2;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    public class ViewHolder {
        Button tv;
    }

    public void setBackgroundDrawable(int position, ViewHolder holder) {
        // Set background drawable for question number based on status
        switch (answerDetails.get(position).status) {
            case Constants.CORRECT:
                if (answerDetails.get(position).bookmarked) {
                    ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
                    params.width = 140;
                    params.height = 140;
                    holder.tv.setLayoutParams(params);
                    holder.tv.setBackgroundResource(R.drawable.correct_star);
                } else
                    holder.tv.setBackgroundResource(R.drawable.circle_question_number_correcct);
                break;
            case Constants.INCORRECT:
                if (answerDetails.get(position).bookmarked) {
                    ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
                    params.width = 140;
                    params.height = 140;
                    holder.tv.setLayoutParams(params);
                    holder.tv.setBackgroundResource(R.drawable.incorrect_star);
                } else
                    holder.tv.setBackgroundResource(R.drawable.circle_question_number_incorrect);
                break;
            case Constants.AVAILABLE:
                holder.tv.setBackgroundResource(R.drawable.circle_question_number_available);
                break;
            case Constants.UNAVAILABLE:
                holder.tv.setBackgroundResource(R.drawable.circle_question_number_unavailable);
                break;

        }
    }

    public void setOnClickListeners(final int position, final ViewHolder holder) {
        switch (answerDetails.get(position).status) {
            case Constants.CORRECT:
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, QuestionsActivity.class);
                        intent.putExtra(Constants.BUNDLE_QUESTION_NUMBER, getItem(position).question_number);
                        intent.putExtra(Constants.BUNDLE_QUESTION_CATEGORY, getItem(position).category);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        context.setAnimationRunning(false); // Stop the background color change animation on leaving activity
                    }
                });
                break;
            case Constants.INCORRECT:
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "This question is locked", Toast.LENGTH_LONG).show();
                        numberLineCallback.openCharacterDialog(Constants.INCORRECT);
                    }
                });
                break;
            case Constants.AVAILABLE:
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, QuestionsActivity.class);
                        intent.putExtra(Constants.BUNDLE_QUESTION_NUMBER, getItem(position).question_number);
                        intent.putExtra(Constants.BUNDLE_QUESTION_CATEGORY, getItem(position).category);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        context.setAnimationRunning(false);             // Stop the background color change animation on leaving activity
                    }
                });
                break;
            case Constants.UNAVAILABLE:
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "This question is unavailable", Toast.LENGTH_LONG).show();
                        numberLineCallback.openCharacterDialog(Constants.UNAVAILABLE);
                    }
                });
                break;
        }
    }

    public void setAnswerDetails(ArrayList<GenericAnswerDetails> answerDetails) {
        this.answerDetails = answerDetails;
    }
}