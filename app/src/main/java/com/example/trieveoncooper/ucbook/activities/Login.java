package com.example.trieveoncooper.ucbook.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieveoncooper.ucbook.R;
import com.example.trieveoncooper.ucbook.fragments.LoginFragment;
import com.example.trieveoncooper.ucbook.live.BaseLiveService;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.example.trieveoncooper.ucbook.live.BaseLiveService.FIRE_BASE_REFERENCE;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static boolean checked = false;
    Fragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
         Firebase.setAndroidContext(this);
        BaseLiveService b = new BaseLiveService();

        loginFragment = new Fragment();
        Button loginButton = (Button)findViewById(R.id.loginBUtton);
        final TextView emailField = (TextView)findViewById(R.id.emailField) ;
        final TextView passwordField = (TextView)findViewById(R.id.passwordField) ;
        final RadioButton radioButton = (RadioButton)findViewById(R.id.radioButton);
        Button googleButton = (Button)findViewById(R.id.googleButton);
        Button facebookButton = (Button)findViewById(R.id.facebookButton);
        Button fingerButton = (Button)findViewById(R.id.fingerButton);

        radioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checked = !checked;
                radioButton.setChecked(checked);
            }
        });
        googleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(Login.this, GoogleSignInActivity.class);
                Login.this.startActivity(activityChangeIntent);
            }

        });
        fingerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(Login.this, FingerAuthActivity.class);
                Login.this.startActivity(activityChangeIntent);
            }

        });
        facebookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(Login.this, FaceBookSignInActivity.class);
                Login.this.startActivity(activityChangeIntent);
            }

        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(emailField.getText()!=null && passwordField.getText()!=null) {

                    String email = emailField.getText().toString();
                    String  password = passwordField.getText().toString();
                    Log.d("a", "aunethincating");
                    RadioButton radioButton = (RadioButton)findViewById(R.id.radioButton);
                    if(radioButton.isChecked()) {
                        signIn(email,password);
                    }else{
                        createUser(email,password);
                    }


                }
            }
        });
        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    public void createUser(String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("a", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Firebase reference = new Firebase(FIRE_BASE_REFERENCE);
                            Firebase ref = reference.child("data").child("users").child("3");
                            ref.child("name").setValue("trey4");
                            ref.child("emai").setValue("treysEmai4l");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sa", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("a", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("a", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
