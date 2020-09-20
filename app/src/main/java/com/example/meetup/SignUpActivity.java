package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText username, userpassword, useremail, confirmpassword;
    Button sgnup;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        initializeFields();
        mAuth = FirebaseAuth.getInstance();
        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String username1 = username.getText().toString();
        String Pass = userpassword.getText().toString();
        String Confirm = confirmpassword.getText().toString();
        String email = useremail.getText().toString();

            /*if(TextUtils.isEmpty(username1))
            {
            Toast.makeText(context:this,text:"Please Enter username..",Toast.LENGTH_SHORT).show();
            }*/
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter Email..", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Pass)) {
            Toast.makeText(this, "Enter Password..", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Confirm)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        if(!(Pass.equals(Confirm)))
        {
            Toast.makeText(this, "Please enter same passwords...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait while we are creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,Pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent signin = new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(signin);
                            } else {
                                String messageerror = task.getException().toString();
                                Toast.makeText(SignUpActivity.this, "Error" + messageerror, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }


    }


    private void initializeFields() {
        username = (EditText) findViewById(R.id.SUusernamefield);
        useremail = (EditText) findViewById(R.id.SUemailtextfield);
        userpassword = (EditText) findViewById(R.id.SUPasswordtextfield);
        confirmpassword = (EditText) findViewById(R.id.SUconfirmPasswordfield);
        sgnup = (Button) findViewById(R.id.SUbutton);
        loadingBar = new ProgressDialog(this);
    }
}
