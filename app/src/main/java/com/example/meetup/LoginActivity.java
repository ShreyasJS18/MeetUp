package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
   private TextView sgnup;
   private EditText email,password;
   private FirebaseAuth mAuth;

   private ProgressDialog loadingBar;
    private static final String TAG="ERROR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFields();

        mAuth=FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"OnLoginclick");
                allowLogin();

            }
        });
        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"OnSignUpclick");
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
    private void allowLogin()
    {
        String email1=email.getText().toString();
        String pass=password.getText().toString();
        if(TextUtils.isEmpty(email1))
        {
            Toast.makeText(this, "Please Enter Email..", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this, "Please Enter Password..", Toast.LENGTH_SHORT).show();
        }
        else
        {   loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please Wait");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email1,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                       loadingBar.dismiss();
                       Intent main=new Intent(LoginActivity.this,MainActivity.class);
                       startActivity(main);
                    }
                    else
                    {
                        String error=task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    }
                }
            });
        }
    }


    private void initializeFields()
    {   loadingBar=new ProgressDialog(this);
        loginButton=(Button) findViewById(R.id.loginBtn);
        sgnup=(TextView) findViewById(R.id.SignUplink);
        email=(EditText) findViewById(R.id.loginemailtextfield);
        password=(EditText) findViewById(R.id.loginPasswordtextfield);
    }


    }


