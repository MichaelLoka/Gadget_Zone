package com.example.storeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-database-default-rtdb.firebaseio.com/");
    public static final String EXTRA_PHONENUMBER = "com.example.storeapp.Login.EXTRA_PHONENUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get The Icon Beside The Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);

        final EditText phone =findViewById(R.id.phone);
        final EditText password =findViewById(R.id.password);
        final Button loginBrn =findViewById(R.id.loginBtn);
        final TextView registerNowBtn =findViewById(R.id.registerNowBtb);
        loginBrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //basmalla //login to admin
                Intent i = new Intent(Login.this,ManageProducts.class);
                startActivity(i);
                finish();
                //
                final String phoneTxt =phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){

                    Toast.makeText(Login.this, "Please enter your Mobile No. and Password", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //CHECK IF PHONE NO. IS EXIST ON FIREBASE DATABASE
                            if(snapshot.hasChild(phoneTxt))
                            {

                                // mobile is exist in firebase
                                // now check if password entered match with the value in the firebase data

                                final  String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordTxt)){

                                    Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();

                                    // open MainActivity when Successfull login
                                    Intent i = new Intent(Login.this,MainActivity.class);
                                    i.putExtra(EXTRA_PHONENUMBER,phoneTxt);
                                    startActivity(i);
                                    finish();
                                }

                                else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(Login.this, "Wrong Mobile Number", Toast.LENGTH_SHORT).show();

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // open register activity
                startActivity(new Intent(Login.this,Register.class));
            }
        });



    }
}
