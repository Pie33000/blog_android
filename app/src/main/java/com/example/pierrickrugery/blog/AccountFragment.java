package com.example.pierrickrugery.blog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by Belal on 1/23/2018.
 */

public class AccountFragment extends Fragment {
    DatabaseReference mDatabase;
    FirebaseDatabase database;
    private SharedPreferences preferences;
    TextView name;
    TextView birth;
    TextView phone;
    TextView email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView) getView().findViewById(R.id.name);
        birth = (TextView) getView().findViewById(R.id.birth);
        phone = (TextView) getView().findViewById(R.id.phone);
        email = (TextView) getView().findViewById(R.id.email);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user = preferences.getString("user", null);
        String name_value = preferences.getString("name", null);
        String birth_value = preferences.getString("birth", null);
        String phone_value = preferences.getString("phone", null);
        String email_value = preferences.getString("phone", null);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("users");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("account-info", "onChildAdded: " + dataSnapshot.toString());
                User user = new User(dataSnapshot.child("firstname").getValue().toString(), dataSnapshot.child("lastname").getValue().toString(),
                        dataSnapshot.child("birth").getValue().toString(), dataSnapshot.child("phone").getValue().toString(), dataSnapshot.child("email").getValue().toString());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user", user.getEmail());
                editor.putString("name", user.getFirstname() + " " + user.getLastname());
                editor.putString("birth", user.getBirth());
                editor.putString("phone", user.getPhone());
                editor.putString("email", user.getEmail());
                editor.commit();
                String name_final = user.getFirstname().toUpperCase() + " " + user.getLastname().toUpperCase();
                name.setText(name_final);
                birth.setText(user.getBirth());
                phone.setText(user.getPhone());
                email.setText(user.getEmail());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        if(name_value != null){
            name.setText(name_value.toUpperCase());
        }
        if(birth_value != null){
            birth.setText(birth_value);
        }
        if(phone_value != null){
            phone.setText(phone_value);
        }
        if(email_value != null){
            email.setText(email_value);
        }
    }
}
