package com.example.pierrickrugery.blog;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

import java.security.AccessController;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);
        Intent intent = getIntent();
        ArrayList<String> article = intent.getStringArrayListExtra("message");
        TextView title = (TextView) findViewById(R.id.title);
        TextView content = (TextView) findViewById(R.id.content);
        ImageButton closeButton = (ImageButton) findViewById(R.id.close);
        title.setText(article.get(0));
        content.setText(article.get(1));
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(DetailsActivity.this, MainActivity.class));
            }

        });

        //Log.d("piepie", "onCreate: " + intent.getStringArrayListExtra("message"));

    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
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
