package com.example.abdirahman.movielist.MovieDALC;

import android.util.Log;

import com.example.abdirahman.movielist.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieList {

    private final String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=1396960c122fb1696773a4d42f994866";

    private final String TAG = "MOVIE";

    ArrayList<Movie> movies;

    public MovieList() {
        movies = new ArrayList<Movie>();
    }


    public void loadAll() {
        try {
            String result = getContent(URL);

            if (result == null) return;

            //get the JSON with movies
            JSONObject object = new JSONObject(result);

            JSONArray array = object.optJSONArray("results");

            //goes through the JSON array and converts to Movie objects
            for (int i = 0; i < array.length(); i++) {
                JSONObject d = array.getJSONObject(i);
                int id = Integer.parseInt(d.getString("id"));
                String title = d.getString("title");
                String release = d.getString("release_date");
                String overview = d.getString("overview");
                String imageURL = "http://image.tmdb.org/t/p/w300/" + d.getString("poster_path");
                Movie s = new Movie(id, title, release, overview, imageURL, false);
                movies.add(s);
            }

        } catch (JSONException e) {
            Log.e(TAG,
                    "There was an error parsing the JSON", e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d(TAG, "General exception in loadAll " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Movie> getAll() {
        return movies;
    }


    /**
     * Get the content of the url as a string. Based on using a scanner.
     *
     * @param urlString - the url must return data typical in either json, xml, csv etc..
     * @return the content as a string. Null is something goes wrong.
     */
    private String getContent(String urlString) {
        StringBuilder sb = new StringBuilder();
        try {

            java.net.URL url = new URL(urlString);
            Scanner s = new Scanner(url.openStream());

            while (s.hasNextLine()) {
                String line = s.nextLine();
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

}
