package com.company.e_summit;

public class EventsModel {
    public String title, time, venue, description;

    public EventsModel() {
    }

    public EventsModel(String title, String time, String venue, String description) {
        this.title = title;
        this.time = time;
        this.venue = venue;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }
}
