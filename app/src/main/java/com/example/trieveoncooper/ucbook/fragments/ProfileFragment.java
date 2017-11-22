package com.example.trieveoncooper.ucbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trieveoncooper.ucbook.Classes.User;
import com.example.trieveoncooper.ucbook.R;
import com.example.trieveoncooper.ucbook.activities.Menu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import static com.example.trieveoncooper.ucbook.fragments.LoginFragment.user;
import static com.example.trieveoncooper.ucbook.live.BaseLiveService.readReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
View view;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView nameField= (TextView)view.findViewById(R.id.accountNameDisplay);
        TextView bioField= (TextView)view.findViewById(R.id.accountBioDisplay);
        nameField.setText(user.getName());
        bioField.setText(user.getBio());
        Button b = (Button)view.findViewById(R.id.startButton);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Intent activityChangeIntent = new Intent(getActivity(), Menu.class);
               // getActivity().startActivity(activityChangeIntent);
            }
        });

        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("a","in onstart profile");

        retrieve();

    }
    public void retrieve(){
        final Firebase reference = new Firebase(readReference);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;
                for(DataSnapshot dataSnapShot: dataSnapshot.getChildren()){
                    User userFirebase = dataSnapShot.getValue(User.class);
                    String tempEmail = (String)userFirebase.getEmail();
                    String tempPassword = (String)userFirebase.getPassword();


                    if(user.getEmail().equals(tempEmail) && user.getPassword().equals(tempPassword)) {
                        user = userFirebase;
                        final TextView nameField = (TextView)view.findViewById(R.id.accountNameDisplay);
                        final TextView bioField = (TextView)view.findViewById(R.id.accountBioDisplay);
                        nameField.setText(user.getName());
                        bioField.setText(user.getBio());


                    }

                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}
