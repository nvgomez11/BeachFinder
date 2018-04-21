package com.example.nelson.beachfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Edward on 20/4/2018.
 */

public class GridCommentsAdapter extends BaseAdapter {


    private ArrayList<String> commentAutor;
    private ArrayList<String> comment;
    private Context context;
    private LayoutInflater inflater;


    public GridCommentsAdapter(Context context, ArrayList<String> commentAutor, ArrayList<String> comment){
        this.context = context;
        this.commentAutor = commentAutor;
        this.comment = comment;
    }
    @Override
    public int getCount() {
        return commentAutor.size();
    }

    @Override
    public Object getItem(int i) {
        return commentAutor.get(i);
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
            gridView = inflater.inflate(R.layout.content_comments_inflator,null);
        }
        TextView grid_commentAutor = (TextView) gridView.findViewById(R.id.grid_commentAutor);
        TextView grid_comment = (TextView) gridView.findViewById(R.id.grid_comment);


        grid_commentAutor.setText(commentAutor.get(position).toString());
        grid_comment.setText(comment.get(position).toString());


        return gridView;
    }
}

