package com.example.trieveoncooper.ucbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trieveoncooper.ucbook.Classes.User;
import com.example.trieveoncooper.ucbook.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import static com.example.trieveoncooper.ucbook.fragments.LoginFragment.user;
import static com.example.trieveoncooper.ucbook.live.BaseLiveService.readReference;

public class FirstLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView nameField = (TextView)findViewById(R.id.nameField);
        final TextView bioField = (TextView)findViewById(R.id.bioField);
        Button exitButton = (Button)findViewById(R.id.Done);
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(nameField.getText()!=null && bioField.getText()!=null) {
                    user.setFirstLogin();
                    addFirstTimeInformation();


                  //  Intent activityChangeIntent = new Intent(FirstLoginActivity.this, Hub.class);
                   // startActivity(activityChangeIntent);
                }
            }
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        TextView welcomeField = (TextView)findViewById(R.id.welcomeText);
        welcomeField.setText("I see this is your first time logging in"+user.getEmail());

    }
    public void addFirstTimeInformation(){
        final Firebase reference = new Firebase(readReference);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;
                for(DataSnapshot dataSnapShot: dataSnapshot.getChildren()){
                    User userFirebase = dataSnapShot.getValue(User.class);
                    String tempEmail = (String)userFirebase.getEmail();
                    String tempPassword = (String)userFirebase.getPassword();

                    // User user = new User(userFirebase.getEmail(),userFirebase.getPassword());

                    if(user.getEmail().equals(tempEmail) && user.getPassword().equals(tempPassword)) {
                        Log.d("a","Found in first loginpage");
                        final TextView nameField = (TextView)findViewById(R.id.nameField);
                        final TextView bioField = (TextView)findViewById(R.id.bioField);
                        dataSnapShot.getRef().child("name").setValue(nameField.getText().toString());
                        dataSnapShot.getRef().child("bio").setValue(bioField.getText().toString());

                    }

                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}

