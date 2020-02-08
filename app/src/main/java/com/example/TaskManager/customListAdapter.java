package com.example.TaskManager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class customListAdapter extends ArrayAdapter<Task> {


    private LayoutInflater inflater;
    private ArrayList<Task> arrayList;
    private int viewResourceId;

    public customListAdapter(Context context, int viewResourceId, ArrayList<Task> arrayList ) {

        super(context, viewResourceId, arrayList);
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(viewResourceId, null);

        Task task = arrayList.get(position);

        if (task != null) {

            TextView title = (TextView) convertView.findViewById(R.id.textFirstName);
            TextView category = (TextView) convertView.findViewById(R.id.textLastName);
            TextView state = convertView.findViewById(R.id.state);


            if (title != null) {
                title.setText(task.getName());
            }
            if (category != null) {
                category.setText((task.getCategory()));
            }
            if (state != null) {
                if(task.getState().equalsIgnoreCase("ONDOING")) {
                state.setBackgroundColor(Color.rgb(20, 204, 217));
                } else if (task.getState().equalsIgnoreCase("OPEN")) {
                  state.setBackgroundColor(Color.rgb(39, 194, 31));
                }  else if (task.getState().equalsIgnoreCase("CLOSE")) {
                    state.setBackgroundColor(Color.rgb(179, 12, 12));
                }
            }
        }


        return convertView;
    }

}
