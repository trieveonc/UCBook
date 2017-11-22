package com.example.trieveoncooper.ucbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.trieveoncooper.ucbook.Classes.User;
import com.example.trieveoncooper.ucbook.R;
import com.example.trieveoncooper.ucbook.activities.FirstLoginActivity;
import com.example.trieveoncooper.ucbook.activities.Hub;
import com.example.trieveoncooper.ucbook.activities.Login;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.trieveoncooper.ucbook.live.BaseLiveService.FIRE_BASE_REFERENCE;
import static com.example.trieveoncooper.ucbook.live.BaseLiveService.readReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    View view;
    public static User user;
    public static int counter = 0;
    public static String email="";
    public static String password="";
    protected static boolean found = false;
    public static boolean checked = false;
    private FirebaseAuth mAuth;
    protected static FirebaseDatabase database;
    protected DatabaseReference myRef;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        Firebase.setAndroidContext(getActivity());
        Firebase reference = new Firebase(FIRE_BASE_REFERENCE);
        Firebase ref = reference.child("data").child("users").child("2");
        ref.child("name").setValue("trey");
        ref.child("emai").setValue("treysEmail");

        Button loginButton = (Button)view.findViewById(R.id.loginBUtton);
        final TextView emailField = (TextView)view.findViewById(R.id.emailField) ;
        final TextView passwordField = (TextView)view.findViewById(R.id.passwordField) ;
        final RadioButton radioButton = (RadioButton)view.findViewById(R.id.radioButton);

        radioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checked = !checked;
                radioButton.setChecked(checked);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(emailField.getText()!=null && passwordField.getText()!=null) {

                    email = emailField.getText().toString();
                    password = passwordField.getText().toString();
                    Log.d("a", "aunethincating");
                    RadioButton radioButton = (RadioButton)view.findViewById(R.id.radioButton);
                    if(radioButton.isChecked()) {
                        retrieve();
                    }else{
                        authenticate();
                    }


                }
            }
        });


        return view;
    }

    public boolean authenticate(){
       DatabaseReference mDatabase;
// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("a", "THE DATABASE  : "+mDatabase);

        User user = new User("sda", email);

        mDatabase.child("users").child("id1").setValue(user);

        Firebase r = new Firebase(FIRE_BASE_REFERENCE);
        r.child("users").push();



        counter++;



        return false;
    }

    public void retrieve(){
        final Firebase reference = new Firebase(FIRE_BASE_REFERENCE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;
                for(DataSnapshot dataSnapShot: dataSnapshot.getChildren()){
                    User userFirebase = dataSnapShot.getValue(User.class);
                    String tempEmail = (String)userFirebase.getEmail();
                    String tempPassword = (String)userFirebase.getPassword();

                    // User user = new User(userFirebase.getEmail(),userFirebase.getPassword());

                    Log.d("a","x: "+tempEmail+"x: "+email);
                    if(email.equals(tempEmail) && password.equals(tempPassword)) {

                        user = userFirebase;
                        Log.d("a","firstloginv= "+user.isFirstLogin());

                        if(user.isFirstLogin()) {
                            dataSnapShot.getRef().child("firstLogin").setValue(false);
                            Intent activityChangeIntent = new Intent(getActivity(), FirstLoginActivity.class);
                            getActivity().startActivity(activityChangeIntent);
                        }else{
                            Log.d("a","HELLOMYNAMEISTREYCOOPPERSDAA");

                            Intent activityChangeIntent = new Intent(getActivity(), Hub.class);
                            getActivity().startActivity(activityChangeIntent);
                        }
                    }

                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    public void delete(){
        final Firebase reference = new Firebase(readReference);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;
                for(DataSnapshot dataSnapShot: dataSnapshot.getChildren()){

                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}
