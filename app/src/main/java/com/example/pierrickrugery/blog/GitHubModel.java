package com.example.pierrickrugery.blog;

public class GitHubModel {

    private String name;
    private String description;
    private  String url;
    private String language;

    public GitHubModel(String name, String description, String url, String language){
        if(name != null && name != ""){
            this.name = name;
        }
        if (description != null && description != ""){
            this.description = description;
        }
        if (url != null && url != ""){
            this.url = url;
        }
        if (language != null && language != ""){
            this.language = language;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
