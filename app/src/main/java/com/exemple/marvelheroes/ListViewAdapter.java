package com.exemple.marvelheroes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 9/5/2017.
 */

public class ListViewAdapter extends ArrayAdapter<NYT> {

    //the hero list that will be displayed
    private List<NYT> mNYTList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapter(List<NYT> NYTList, Context mCtx) {
        super(mCtx, R.layout.list_items, NYTList);
        this.mNYTList = NYTList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewImageUrl = listViewItem.findViewById(R.id.textViewImageUrl);
        ImageView toto = listViewItem.findViewById(R.id.ImageView);


        //Getting the hero for the specified position
        NYT NYT = mNYTList.get(position);

        //setting hero values to textviews
        textViewName.setText(NYT.getTitle());
        textViewImageUrl.setText(NYT.getUrl());
        String imgUrl = NYT.getImage();
        Picasso.get().load(imgUrl).into(toto);

        //returning the listitem
        return listViewItem;
    }
}