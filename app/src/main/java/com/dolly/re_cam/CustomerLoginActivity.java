package com.dolly.re_cam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerLoginActivity extends AppCompatActivity {

    private FirebaseAuth authentication;
    private FirebaseAuth.AuthStateListener AuthListener;


    private EditText Email, Password;
    private Button Registration, Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        authentication = FirebaseAuth.getInstance(); // get current login state

        AuthListener   = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user!=null){

                    Intent intent = new Intent(CustomerLoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        Email    = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        Registration = (Button) findViewById(R.id.registration);
        Login        = (Button) findViewById(R.id.login);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String strEmail    = Email.getText().toString();
                final String strPassword = Password.getText().toString();

                authentication.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){
                            Toast.makeText(CustomerLoginActivity.this, "Sign in error",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_id = authentication.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(user_id);
                            current_user_db.setValue(true);

                        }
                    }
                });

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strEmail    = Email.getText().toString();
                final String strPassword = Password.getText().toString();

                authentication.signInWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){
                            Toast.makeText(CustomerLoginActivity.this, "Sign in error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        authentication.addAuthStateListener(AuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        authentication.removeAuthStateListener(AuthListener);
    }
}
