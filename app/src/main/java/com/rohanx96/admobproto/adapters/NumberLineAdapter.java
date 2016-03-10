package com.rohanx96.admobproto.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.rohanx96.admobproto.elements.GenericAnswerDetails;
import com.rohanx96.admobproto.ui.NumberLineActivity;
import com.rohanx96.admobproto.ui.QuestionsActivity;
import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.utils.Constants;

import java.util.ArrayList;

/**
 * Created by bhutanidhruv16 on 07-Mar-16.
 */
public class NumberLineAdapter extends BaseAdapter {

    NumberLineActivity context;
    ArrayList<GenericAnswerDetails> answerDetails;
    LayoutInflater inflater;

    public NumberLineAdapter(NumberLineActivity context, ArrayList<GenericAnswerDetails> answerDetails) {
        this.context = context;
        this.answerDetails = answerDetails;
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
        *Process answer details here and set backgrounds and lock image accordingly
         */

        if (position != 0 && position != answerDetails.size() - 1)
            holder.tv.setText(" " + getItem(position).question_number + " ");

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionsActivity.class);
                /*
                *send position of clicked item. Note, it sends (position-1) because there is an extra element padded at top and bottom of numberline
                 */
                intent.putExtra(Constants.BUNDLE_QUESTION_POSITION, (position - 1));
                intent.putExtra(Constants.BUNDLE_QUESTION_CATEGORY, getItem(position - 1).category);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                context.setAnimationRunning(false); // Stop the background color change animation on leaving activity
            }
        });

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
}