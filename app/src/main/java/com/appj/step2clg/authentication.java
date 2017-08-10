package com.appj.step2clg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appj.step2clg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class authentication extends AppCompatActivity implements View.OnClickListener {
    private TextView buttonRegister;
    private Button buttonsignin;
    private Button buttonsignout;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressdialog;
    private TextView temp;
    private  String email=null;
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);

                       /* Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                                .getBoolean("isApurvaCloseAPP", true);*/
                    }
                }).setNegativeButton("no", null).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SharedPreferences sp = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isFirstRun", true);
        System.out.println("first run editing to true");
        editor.apply();
        editor.commit();


        progressdialog = new ProgressDialog(authentication.this);
        progressdialog.setMessage("Please Wait....");
        buttonRegister = (TextView) findViewById(R.id.reg);
        buttonsignin = (Button)findViewById(R.id.tvsignin);
        editTextEmail = (EditText) findViewById(R.id.editTextemail);
        editTextPassword = (EditText) findViewById(R.id.editTextpassword);
        temp = (TextView) findViewById(R.id.temptext);
//to disable button
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            //Toast.makeText(this, "Connection!", Toast.LENGTH_SHORT).show();
        }else
        {
            buttonsignin.setEnabled(false);
            buttonRegister.setEnabled(false);
            Toast.makeText(this, "No Connection!", Toast.LENGTH_SHORT).show();
        }

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    email=user.getEmail();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

                // ...
            }
        };
        buttonRegister.setOnClickListener(this);
        buttonsignin.setOnClickListener(this);

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        //  progressdialog.show();
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:failed", task.getException());


                            // [START_EXCLUDE]

                            // hideProgressDialog();
                            // [END_EXCLUDE]
                        }
                    }

                });
    }

    // [END sign_in_with_email]


    private void signOut() {
        mAuth.signOut();

    }

    // @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }






    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            Intent i = new Intent(authentication.this, reg.class);
            startActivity(i);
        }
        if (v == buttonsignin) {
            System.out.println(editTextEmail.getText().toString() + "***");
            System.out.println(editTextPassword.getText().toString() + "+++");
            signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            if (email != null) {
                Intent it1 = new Intent(authentication.this,MainActivity.class);
                startActivity(it1);
                SharedPreferences sp = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isFirstRun", false);
                System.out.println("first run editing to false");
                editor.apply();
                editor.commit();
            }

        }

    }
}






