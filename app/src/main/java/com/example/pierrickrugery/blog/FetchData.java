package com.example.pierrickrugery.blog;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class FetchData extends AsyncTask<Void, Void, ArrayList<GitHubModel>> {
    String data;
    private ArrayList<GitHubModel> gitHubModelArrayList;
    private GithubFragment githubFragment;
    public LocalBroadcastManager manager;
    FetchData fetch = this;

    public FetchData(LocalBroadcastManager manager){
        this.manager = manager;
    }

    @Override
    protected ArrayList<GitHubModel> doInBackground(Void... voids) {
        try{
            gitHubModelArrayList = new ArrayList<>();
            URL url = new URL("https://api.github.com/users/pie33000/repos");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            data = data.substring(4, data.length()-4);
            JSONArray JA = new JSONArray(data);
            JSONObject JO;
            for (int i = 0; i< JA.length(); i++) {
                JO = (JSONObject) JA.get(i);
                GitHubModel gitHubModel = new GitHubModel(JO.get("name").toString(),
                        JO.get("description").toString(), JO.get("url").toString(),
                        JO.get("language").toString());
                this.gitHubModelArrayList.add(gitHubModel);
            }

        }catch (MalformedURLException e){
            e.printStackTrace();

        }catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        return this.gitHubModelArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<GitHubModel> gitHubModels) {
        super.onPostExecute(gitHubModels);
        Intent intent = new Intent("com.action.test");
        //intent.putExtra("key","123");
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> languages = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        for(int i = 0; i< gitHubModelArrayList.size(); i++){
            names.add(gitHubModelArrayList.get(i).getName());
            descriptions.add(gitHubModels.get(i).getDescription());
            languages.add(gitHubModelArrayList.get(i).getLanguage());
            urls.add(gitHubModelArrayList.get(i).getUrl());
        }
        intent.putExtra("names", names);
        intent.putExtra("descriptions", descriptions);
        intent.putExtra("languages", languages);
        intent.putExtra("urls", urls);
        manager.sendBroadcast(intent);
    }


    public ArrayList<GitHubModel> getGitHubModelArrayList() {
        return gitHubModelArrayList;
    }

    public void setGitHubModelArrayList(ArrayList<GitHubModel> gitHubModelArrayList) {
        this.gitHubModelArrayList = gitHubModelArrayList;
    }
}
