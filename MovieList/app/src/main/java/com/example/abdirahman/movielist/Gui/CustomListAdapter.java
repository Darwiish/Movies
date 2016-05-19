package com.example.abdirahman.movielist.Gui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdirahman.movielist.Model.Movie;
import com.example.abdirahman.movielist.R;

import java.util.ArrayList;

public class CustomListAdapter<T extends Movie> extends ArrayAdapter<T> {
    private final Activity context;
    ArrayList<T> values;

    public CustomListAdapter(Activity context, ArrayList<T> values) {
        super(context, R.layout.cell, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cell, parent,false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.littlePoster);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);

        if(values.get(position).getImage()!=null)
        imageView.setImageBitmap(values.get(position).getImage());
        txtTitle.setText(values.get(position).getTitle());
        return rowView;
    };

    public void updateList(ArrayList<T> newData){
        values = newData;
        notifyDataSetChanged();
    }


}
