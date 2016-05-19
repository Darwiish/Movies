package com.example.abdirahman.movielist.Model;

import android.graphics.Bitmap;

/**
 * Created by abdirahman on 26/04/16.
 */
public class Movie {
    private int id;
    private String title ;
    private String release;
    private String overview;
    private Bitmap image;
    private String imageURL;
    private boolean stored;

    public Movie(int id, String title, String release, String overview, String imageURL, boolean stored) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.overview = overview;
        this.imageURL = imageURL;
        this.stored = stored;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }
}
