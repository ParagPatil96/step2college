package com.appj.step2clg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appj.step2clg.adapter.wall_adapter;
import com.appj.step2clg.app.AppController;
import com.appj.step2clg.data.wall_feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class clg_list extends AppCompatActivity {
    String respons;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private wall_adapter listAdapter;
    private List<wall_feed> wall_feeds;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public String field,location;
    public Integer rank,cnt=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clg_list);

        Intent rcv = getIntent();
         rank = Integer.parseInt(rcv.getStringExtra("Rank"));
         field = rcv.getStringExtra("Field");
         location = rcv.getStringExtra("Location");
        System.out.println(rank+"**"+location+" zz "+field);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("clg_list");

        listView = (ListView) findViewById(R.id.list);
        wall_feeds = new ArrayList<wall_feed>();

        listAdapter = new wall_adapter(this, wall_feeds);
        listView.setAdapter(listAdapter);
        addUserChangeListener();

    }

    private void addUserChangeListener() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot topSnapshot) {
                for (DataSnapshot snapshot: topSnapshot.getChildren()) {
                    clg CLG = snapshot.getValue(clg.class);

                    // Check for null
                    System.out.println(rank+"kk"+location+" zz "+CLG.clg_location);
                    if (CLG != null && CLG.clg_location.equals(location)) {
                        System.out.println(rank+"yy"+location+" zz "+field);
                        if((field.equals("Computer Science") && CLG.cs>=rank)||(field.equals("Information Technology") && CLG.it>=rank)||(field.equals("Computer Science") && CLG.cs>=rank)||(field.equals("Mechanical") && CLG.mech>=rank)||(field.equals("Electrical") && CLG.electrical>=rank)||(field.equals("Civil") && CLG.civil>=rank)||(field.equals("Electronics") && CLG.electronics>=rank)||((field.equals("Electronics & Communication")|| field.equals("Electronics & TeleCommunication" ))&& CLG.ec>=rank)||(field.equals("Industrial") && CLG.industrial>=rank)||(field.equals("Instrumentation") && CLG.instrumentation>=rank)) {
                            System.out.println(rank+"xx"+location+" zz "+field);
                            System.out.println("++" + CLG.clg_name);
                            Log.e(TAG, "Data is changed!" + CLG.clg_name);
                            //converting to list array
                            wall_feed item = new wall_feed();
                            item.setId(cnt++);
                            item.setName(CLG.clg_name);

                            // Image might be null sometimes
                            String image = CLG.clg_image == "0" ? null : CLG.clg_image;
                            item.setImge(image);
                            item.setStatus(CLG.clg_vision);
                            String imagep = CLG.clg_logo == "0" ? null : CLG.clg_logo;
                            item.setProfilePic(imagep);
                            item.setTimeStamp(CLG.clg_address);

                            // url might be null sometimes
                            String feedUrl = CLG.clg_url == "0" ? null : CLG.clg_url;
                            item.setUrl(feedUrl);

                            wall_feeds.add(item);
                        }
                    }
                    System.out.println("Null data found");
                }
                listAdapter.notifyDataSetChanged();
                return;
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

}