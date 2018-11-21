package com.example.pierrickrugery.blog;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Article {
    String title;
    String content;
    String date;

    public Article(){

    }

    public Article(String title, String content, String date){
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle(){
        return this.title;
    }
}
