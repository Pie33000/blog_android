package com.example.pierrickrugery.blog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LoginActivity act = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        mAuth = FirebaseAuth.getInstance();
        final Button signIn = (Button) findViewById(R.id.signIn);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        final Button register = (Button) findViewById(R.id.register);
        final TextView error = (TextView) findViewById(R.id.error);






        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("") && !password.getText().toString().equals("")){
                    this.signIn(email.getText().toString(), password.getText().toString());
                }
            }

            private void signIn(String email, String password) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("connection-ok", "onComplete: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("user-id", "onComplete: " + user.getUid());
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            try{
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(act);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("user", user.getEmail());
                                editor.commit();
                            }catch (NullPointerException e){
                                error.setText("Check your information");
                            }

                            startActivity(intent);
                            //loadFragment(new HomeFragment());
                            if(user.getEmail()!= null){

                                Log.d("connection-ok", "onComplete: " + user.getEmail());
                            }
                        }else{
                            error.setText("Check your information");
                            Log.d("connection-ok", "onComplete: failed");
                        }
                    }
                });

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }


}
