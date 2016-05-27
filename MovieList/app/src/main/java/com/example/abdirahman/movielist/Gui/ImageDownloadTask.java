package com.example.abdirahman.movielist.Gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.abdirahman.movielist.Model.Movie;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ImageDownloadTask extends AsyncTask<ArrayList<Movie>, Void, ArrayList<Movie>>
    {
    CustomListAdapter list;

    public ImageDownloadTask(CustomListAdapter list)
    {
        this.list = list;
    }
    @Override
    protected ArrayList<Movie> doInBackground(ArrayList<Movie>... movies) {
        ArrayList<Movie> result = movies[0];
        for(Movie m : result) {
            String urlOfImage = m.getImageURL();
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            float scale = 0.4F;
            Bitmap bm = Bitmap.createScaledBitmap(logo, (int) (logo.getWidth() * scale), (int) (logo.getHeight() * scale), true);
            m.setImage(bm);
        }
        return result;
    }

    @Override
    protected void onPostExecute(final ArrayList<Movie> newData) {
        list.updateList(newData);
    }
}

