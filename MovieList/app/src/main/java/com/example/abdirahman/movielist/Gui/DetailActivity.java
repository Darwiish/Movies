package com.example.abdirahman.movielist.Gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abdirahman.movielist.MovieDALC.DAO;
import com.example.abdirahman.movielist.Model.Movie;
import com.example.abdirahman.movielist.R;


public class DetailActivity extends AppCompatActivity {

    private DAO dao;
    private Movie movie;
    private ImageView btnStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the values parsed from MainActivity
        final Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        String title = extras.getString("title");
        String release = extras.getString("release");
        String ov = extras.getString("overview");
        String imageUrl = extras.getString("imageUrl");
        boolean stored = extras.getBoolean("stored");
        Bitmap bitmap = (Bitmap) extras.get("image");

        //creates an Movie object from the recieved values
        movie = new Movie(id, title, release, ov, imageUrl, stored);
        movie.setImage(bitmap);

        Log.e("MovieList", "Received movie: " + movie.toString());

        final ImageView image = (ImageView) findViewById(R.id.poster);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        TextView overview = (TextView) findViewById(R.id.overview);
        btnStore = (ImageButton) findViewById(R.id.btnStore);

        image.setImageBitmap(movie.getImage());
        releaseDate.setText(movie.getRelease());
        overview.setText(movie.getOverview());

        updateImageButton();


        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = DAO.getInstance();
                movie.setStored(!movie.isStored());

                if (dao.getMovie(movie.getId()) != null)
                    dao.updateMovie(movie.getId(), movie);
                else
                    dao.addMovie(movie);
                updateImageButton();
                Log.e("MovieList", "Status changed to: " + movie.isStored());
            }
        });
    }

    private void updateImageButton() {
        if (movie.isStored())
            btnStore.setImageResource(android.R.drawable.star_on);
        else
            btnStore.setImageResource(android.R.drawable.star_off);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        i.putExtra("id", movie.getId());
        i.putExtra("stored", movie.isStored());
        setResult(Activity.RESULT_OK, i);
        Log.e("MovieList", "Send intent " + i.toString());
        finish();
    }
}
