package com.company.e_summit;

public class AnnouncementsModel {
    public String title, description, type, link;

    public AnnouncementsModel() {
    }

    public AnnouncementsModel(String title, String description, String type, String link) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }
}
