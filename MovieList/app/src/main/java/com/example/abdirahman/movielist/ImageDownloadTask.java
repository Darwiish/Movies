package com.example.abdirahman.movielist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.abdirahman.movielist.DALC.Movie;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ImageDownloadTask extends AsyncTask<ArrayList<Movie>, Void, ArrayList<Movie>> // output of doInBackground
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
                InputStream is = new URL(urlOfImage).openStream();//downloads the stream of binary data
                logo = BitmapFactory.decodeStream(is);//converts the binary data from stream to bitmap
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            //scale down the image to 40%
            float scale = 0.4F;
            Bitmap bm = Bitmap.createScaledBitmap(logo, (int) (logo.getWidth() * scale), (int) (logo.getHeight() * scale), true);
            m.setImage(bm);
        }
        return result;
    }

    @Override
    protected void onPostExecute(final ArrayList<Movie> newData) {
        list.updateList(newData); //update the movies in the adapter with a new movies(that have images)
    }
}

