package com.rohanx96.admobproto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.rohanx96.admobproto.R;
import com.rohanx96.admobproto.elements.SequenceAnswersDetails;
import com.rohanx96.admobproto.utils.Constants;

import java.util.ArrayList;

/**
 * Created by bhutanidhruv16 on 07-Mar-16.
 */
public class NumberLineAdapter extends BaseAdapter {

    Context context;
    ArrayList<SequenceAnswersDetails> sequenceAnswersDetails;
    LayoutInflater inflater;

    public NumberLineAdapter(Context context, ArrayList<SequenceAnswersDetails> sequenceAnswersDetails) {
        this.context = context;
        this.sequenceAnswersDetails = sequenceAnswersDetails;
        this.sequenceAnswersDetails.add(0, new SequenceAnswersDetails(-1, Constants.CORRECT, false, false, false));
        this.sequenceAnswersDetails.add(sequenceAnswersDetails.size(), new SequenceAnswersDetails(-1, Constants.CORRECT, false, false, false));
    }

    @Override
    public int getCount() {
        return sequenceAnswersDetails.size();
    }

    @Override
    public SequenceAnswersDetails getItem(int position) {
        return sequenceAnswersDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
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

        if (position != 0 && position != sequenceAnswersDetails.size() - 1)
            holder.tv.setText(" " + getItem(position).question_id + " ");

        return vi;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return -1;
        else if (position == sequenceAnswersDetails.size() - 1)
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