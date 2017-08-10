package com.appj.step2clg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.appj.step2clg.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
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
import java.util.Arrays;


public class Engineering extends Fragment implements  AdapterView.OnItemSelectedListener {
    RadioGroup clevel;
    LinearLayout cl,l;
    RadioButton level,in,mh;
    Button search;
    EditText rank;
    Spinner F,L,LL;
    boolean flag=true;
    String sfield,slocation,Rank,respons,typee;
    ArrayAdapter ll,ff,lll;
    String[] fields = { "All", "Computer Science", "Information Technology", "Mechanical", "Electrical","Civil" ,"Electronics","Electronics & Communication","Electronics & TeleCommunication","Industrial","Instrumentation"};
    String[] locations = { "All", "Mumbai", "Pune", "Nagpur", "Jalgaon","Aurangabad","Amravati","Nanded","Solapur","Kolhapur" };
    String[] clocations = { "All", "Maharashtra", "Delhi", "Rajsthan", "Bangal","Kerala","Karnatak","Chattisgadh" };


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_engineering, container, false);


        //Radio button
        cl = (LinearLayout) v.findViewById(R.id.cl);
        l = (LinearLayout) v.findViewById(R.id.l);
        clevel = (RadioGroup) v.findViewById(R.id.radioButton);
        in = (RadioButton) v.findViewById(R.id.radioButton1);
        in.setEnabled(false);
        mh = (RadioButton) v.findViewById(R.id.radioButton2);
        clevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                level = (RadioButton) getActivity().findViewById(checkedId);
                typee = level.getText().toString();
                if(in==level) {
                    cl.setVisibility(View.VISIBLE);
                    l.setVisibility(View.INVISIBLE);
                }
                else {
                    l.setVisibility(View.VISIBLE);
                    cl.setVisibility(View.INVISIBLE);
                }
                System.out.println("selected="+typee);
            }
        });


        //Edit text
        rank = (EditText) v.findViewById(R.id.Rank);

        // int srank = Integer.parseInt(Rank);

        //spinner
        F = (Spinner) v.findViewById(R.id.fields);
        L = (Spinner) v.findViewById(R.id.clocations);
        LL= (Spinner) v.findViewById(R.id.llocations);
        F.setOnItemSelectedListener(this);
        L.setOnItemSelectedListener(this);
        LL.setOnItemSelectedListener(this);

        ff = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,fields);
        ff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        F.setAdapter(ff);

        ll = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,clocations);
        ll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        L.setAdapter(ll);

        lll = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,locations);
        lll.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LL.setAdapter(lll);

        search = (Button) v.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rank= rank.getText().toString().trim();
                System.out.println("*"+Rank+"*");
                System.out.println("Field="+sfield);
                System.out.println("Location="+slocation);
                Intent i = new Intent(getActivity(), clg_list.class);
                i.putExtra("Type", typee);
                i.putExtra("Rank",Rank);
                i.putExtra("Field",sfield);
                i.putExtra("Location",slocation);
                startActivity(i);
            }
        });

        return v;
    }






    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

        Spinner spinner = (Spinner) arg0;
        if(spinner.getId() == R.id.fields)
            sfield = fields[position];
        else if(spinner.getId() == R.id.llocations)
            slocation = locations[position];
        else if(spinner.getId() == R.id.clocations)
            slocation = clocations[position];


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }



}

