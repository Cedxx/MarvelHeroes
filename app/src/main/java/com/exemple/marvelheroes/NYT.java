package com.exemple.marvelheroes;

public class NYT {    String title, url, image;

    public NYT(String title, String url, String image) {
        this.title = title;
        this.url = url;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }


    public String getImage() {
        return image;
    }
}
