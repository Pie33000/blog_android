package com.example.pierrickrugery.blog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    DatabaseReference mDatabase;
    FirebaseDatabase database;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<Integer> imgs = new ArrayList<>(Arrays.asList(R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp));


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final MyAdapter adapter = new MyAdapter(getContext(), titles, description, imgs);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("articles");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    titles.add(dataSnapshot.child("title").getValue().toString());
                    description.add(dataSnapshot.child("content").getValue().toString());
                    imgs.add(R.drawable.ic_date_range_black_24dp);
                    MyAdapter adapter = new MyAdapter(getContext(), titles, description, imgs);
                    setListAdapter(adapter);
                }
                catch (Exception e){
                    Log.d("Error", "onChildAdded Error: " + e.getMessage());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("children", "onChildRemoved: " + dataSnapshot.getValue());
                try{
                    if (titles.contains(dataSnapshot.child("title").getValue().toString())) {
                        int index = titles.indexOf(dataSnapshot.child("title").getValue().toString());
                        titles.remove(index);
                        description.remove(index);
                        imgs.remove(index);
                        MyAdapter adapter = new MyAdapter(getContext(), titles, description, imgs);
                        setListAdapter(adapter);
                    }
                }
                catch (Exception e){
                    Log.d("Error", "onChildRemoved Error: " + e.getMessage());
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Item" + position, Toast.LENGTH_SHORT).show();
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> myTitle;
        ArrayList<String> myDescription;
        ArrayList<Integer> images;

        MyAdapter(Context c, ArrayList<String> titles, ArrayList<String> description, ArrayList<Integer> imgs) {
            super(c, R.layout.row, R.id.title, titles);
            this.context = c;
            this.myTitle = titles;
            this.myDescription = description;
            this.images = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            //ImageView images = row.findViewById(R.id.logo);
            TextView myTitle = row.findViewById(R.id.title);
            TextView date = row.findViewById(R.id.date);
            //images.setImageResource(imgs.get(position));
            myTitle.setText(titles.get(position));
            date.setText(description.get(position));
            return super.getView(position, convertView, parent);
        }

    }
}
