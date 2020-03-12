package com.exemple.marvelheroes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //the URL having the json data
    private static final String JSON_URL = "https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=k5Eg30P0RAAy4bav3zB7RBXK5NrPjjCv";

    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<NYT> mNYTList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing listview and hero list
        listView = findViewById(R.id.listView);
        mNYTList = new ArrayList<>();

        //this method will fetch and parse the data
        loadNewsList();
    }

    private void loadNewsList() {
        //getting the progressbar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray newsArray = obj.getJSONArray("results");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < newsArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = newsArray.getJSONObject(i);
                                JSONArray mediaArray = heroObject.getJSONArray("media");
                                JSONObject mediaObject = mediaArray.getJSONObject(0);
                                JSONArray mediaData = mediaObject.getJSONArray("media-metadata");
                                JSONObject toto = mediaData.getJSONObject(1);



                                //creating a hero object and giving them the values from json object
                                NYT NYT = new NYT(heroObject.getString("title"), heroObject.getString("url"), toto.getString("url"));

                                //adding the hero to herolist
                                mNYTList.add(NYT);
                            }

                            //creating custom adapter object
                            ListViewAdapter adapter = new ListViewAdapter(mNYTList, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}