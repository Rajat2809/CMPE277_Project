package com.application.project.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;



public class CustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public CustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    public void addItem(String item)
    {
        list.add(item);
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.food_list_item, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.food_item);
        listItemText.setText(list.get(position));

        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        Button detailsButton = (Button)view.findViewById(R.id.detailsButton);
        Button editButton = (Button)view.findViewById(R.id.editButton);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final DatabaseHelper db = new DatabaseHelper(context);
                db.delete(db.getWritableDatabase(), getItem(position).toString());
                list.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Food Deleted", Toast.LENGTH_LONG).show();
            }
        });


        detailsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();

                Intent intent = new Intent(context,FoodDetailsActivity.class);
                intent.putExtra("Food Name",getItem(position).toString());
                context.startActivity(intent);
            }
        });


        return view;
    }
}