package com.example.pierrickrugery.blog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;


public class GithubFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public static String data = "";
    public LocalBroadcastManager manager;
    private ArrayList<String> names;
    private ArrayList<String> descriptions;
    private ArrayList<String> languages;
    private ArrayList<String> urls;
    GithubFragment git = this;
    private final Context c = getContext();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBroadCastReceiver();
        return inflater.inflate(R.layout.github_fragment, null);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FetchData process = new FetchData(manager);
        process.execute();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Item" + position, Toast.LENGTH_SHORT).show();
    }

    private void initBroadCastReceiver() {
        manager = LocalBroadcastManager.getInstance(getContext());
        MyBroadCastReceiver receiver = new MyBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        //whatever
        filter.addAction("com.action.test");
        manager.registerReceiver(receiver,filter);
    }
    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //e.g
            names = intent.getStringArrayListExtra("names");
            descriptions = intent.getStringArrayListExtra("descriptions");
            languages = intent.getStringArrayListExtra("languages");
            urls = intent.getStringArrayListExtra("urls");
            if(getContext() != null){
                GitHubAdapter adapter = new GitHubAdapter(getContext(), names, descriptions, languages, urls);
                setListAdapter(adapter);
                getListView().setOnItemClickListener(git);
                adapter.notifyDataSetChanged();
                names = null;
                descriptions = null;
                languages = null;
                urls = null;
            }
            }
    }

    class GitHubAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> myName;
        ArrayList<String> myDescription;
        ArrayList<String> myLanguage;
        ArrayList<String> myUrl;

        GitHubAdapter(Context c, ArrayList<String> names, ArrayList<String> descriptions, ArrayList<String> languages, ArrayList<String> urls) {
            super(c, R.layout.row2, R.id.title, names);
            this.context = c;
            this.myName = names;
            this.myDescription = descriptions;
            this.myLanguage = languages;
            this.myUrl = urls;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row2, parent, false);
            TextView myTitle = row.findViewById(R.id.title);
            TextView myDate = row.findViewById(R.id.date);
            TextView myContent = row.findViewById(R.id.content);
            TextView myUrls = row.findViewById(R.id.language);
            myTitle.setText(myName.get(position));
            myDate.setText(myLanguage.get(position));
            myContent.setText(myDescription.get(position));
            myUrls.setText(myUrl.get(position));
            return super.getView(position, convertView, parent);
        }

    }

}
