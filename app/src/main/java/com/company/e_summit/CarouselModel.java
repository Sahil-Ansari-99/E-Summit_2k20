package com.company.e_summit;

public class CarouselModel {
    public String title, link, venue, date, description;

    public CarouselModel() {
    }

    public CarouselModel(String title, String link, String venue, String date, String description) {
        this.title = title;
        this.link = link;
        this.venue = venue;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
