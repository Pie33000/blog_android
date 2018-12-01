package com.example.pierrickrugery.blog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RegisterActivity act = this;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final EditText firstname = (EditText) findViewById(R.id.firstname);
        final EditText lastname = (EditText) findViewById(R.id.lastname);
        final EditText birth = (EditText) findViewById(R.id.datebirth);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        final EditText confirm_password = (EditText) findViewById(R.id.confirm_password);
        final TextView error = (TextView) findViewById(R.id.error);
        Button register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(confirm_password.getText().toString())){
                    signUp(email.getText().toString(), password.getText().toString());
                }
                else{
                    error.setText("Check your password");
                }
            }
            private void signUp(final String email, final String password){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("create-user", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();
                                    User user_object = new User(firstname.getText().toString(), lastname.getText().toString(),
                                            birth.getText().toString(), phone.getText().toString(), email);
                                    mDatabase.child("users").child(userId).setValue(user_object);
                                    try{
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(act);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("user", user.getEmail());
                                        editor.putString("name", firstname.getText().toString()+" "+lastname.getText().toString());
                                        editor.putString("birth", birth.getText().toString());
                                        editor.putString("phone", phone.getText().toString());
                                        editor.putString("email", email);
                                        editor.commit();
                                    }catch (NullPointerException e){
                                        error.setText("Login again, internal error");
                                    }
                                    Intent intent = new Intent(act, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.w("create-user", "createUserWithEmail:failure", task.getException());
                                    error.setText("Check your information, registration failed");
                                }
                            }
                });
            }
        });
    }
}
