package com.example.nelson.beachfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Nelson on 14/4/2018.
 */

public class GridAdapter extends BaseAdapter {

    private int icons[];
    private String title[];
    private String description[];
    private Context context;
    private LayoutInflater inflater;


    public GridAdapter(Context contex, int icons[], String title[],String description[]){
        this.context = context;
        this.icons = icons;
        this.title = title;

    }
    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return title[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if(convertView==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.content_beach_list_inflator,null);
        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);
        TextView grid_titles = (TextView) gridView.findViewById(R.id.titles);
        TextView grid_description = (TextView) gridView.findViewById(R.id.grid_descriptions);

        icon.setImageResource(icons[position]);
        grid_titles.setText(title[position]);
        grid_description.setText(description[position]);

        return gridView;
    }
}
