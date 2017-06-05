package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import butterknife.Bind;
import butterknife.ButterKnife;



public class SignupActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsersRef = mRootRef.child("users");


    private static final String TAG = "SignupActivity";
    @Bind(R.id.input_id)
    EditText _idText;
    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });



    }




    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }else{

            mUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String id = _idText.getText().toString();
                    boolean idAlready = false;

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if(postSnapshot.child("id").getValue().equals(id)){
                            idAlready = true;
                        }
                        }

                        if(idAlready){
                            _signupButton.setEnabled(false);

                            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                                    R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Creating Account...");
                            progressDialog.show();


                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            _idText.setError("Existing ID");
                                            onSignupFailed();

                                            progressDialog.dismiss();
                                        }
                                    }, 3000);



                        }else{
                            _signupButton.setEnabled(false);

                            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                                    R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Creating Account...");
                            progressDialog.show();

                            String name = _nameText.getText().toString();

                            String password = _passwordText.getText().toString();
                            String reEnterPassword = _reEnterPasswordText.getText().toString();


                            mUsersRef.child(id).setValue(new User(id, name, password, 50));

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {

                                            onSignupSuccess();

                                            progressDialog.dismiss();
                                        }
                                    }, 3000);

                        }
                    }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




        }

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        finish();


    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "ID is already used", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String id = _idText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if(id.isEmpty() || id.length() < 4) {
            _idText.setError("at least 4 characters");
            valid = false;
        }else {
            _idText.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}