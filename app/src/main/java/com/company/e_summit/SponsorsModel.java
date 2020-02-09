package com.company.e_summit;

public class SponsorsModel {
    public String name;
    public String title;
    public String url;

    public SponsorsModel() {
    }

    public SponsorsModel(String name, String title, String url) {
        this.name = name;
        this.title = title;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
