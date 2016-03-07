package com.rohanx96.admobproto;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhutanidhruv16 on 07-Mar-16.
 */
public class NumberLineAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> numbers;
    LayoutInflater inflater;

    public NumberLineAdapter(Context context, ArrayList<Integer> numbers) {
        this.context = context;
        this.numbers = numbers;
    }

    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return numbers.get(position);
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
                vi = inflater.inflate(R.layout.element_number_line, null);
                holder.tv = (Button) vi.findViewById(R.id.tv_1);
            } else if (type == 1) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line2, null);
                holder.tv = (Button) vi.findViewById(R.id.tv_2);
            } else if (type == -1) {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line_start, null);
                holder.tv = (Button) vi.findViewById(R.id.uparrow);
            } else {
                inflater = (LayoutInflater) context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.element_number_line_end, null);
                holder.tv = (Button) vi.findViewById(R.id.downarrow);
            }
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();

            // Log.d("NUM CHECK", holder.tv.getText().toString()+"FUCK ME " + position);
        }

        if (position != 0 && position != numbers.size() - 1)
            holder.tv.setText(" " + numbers.get(position) + " ");

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change drawable
            }
        });
        return vi;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return -1;
        else if (position == numbers.size() - 1)
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
