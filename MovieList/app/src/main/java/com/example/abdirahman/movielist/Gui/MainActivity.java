package com.example.abdirahman.movielist.Gui;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.abdirahman.movielist.MovieDALC.DAO;
import com.example.abdirahman.movielist.MovieDALC.DB;
import com.example.abdirahman.movielist.Model.Movie;
import com.example.abdirahman.movielist.MovieDALC.MovieList;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    //Initializes the variables
    String TAG = "MOVIES";
    ArrayList<Movie> movies;
    private CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeTask t = new InitializeTask(this);
        t.execute(new MovieList());
        DAO.createInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            updateListWithDB();
        }
    }

    private void updateListWithDB() {
        Cursor c = DAO.getInstance().getMovies();
        if (c != null) {
            while(c.moveToNext()) {
                int Id = c.getInt(c.getColumnIndex(DB.ID));
                boolean stored = c.getInt(c.getColumnIndex(DB.STORED)) > 0;
                for(Movie m: movies){
                    if(m.getId() == Id){
                        m.setStored(stored);
                    }
                }
            }
            c.close();
        }
        adapter.updateList(movies);
    }

    public void initializeData(final ArrayList<Movie> movies) {
        this.movies = movies;
        adapter = new CustomListAdapter(this, this.movies);
        setListAdapter(adapter);
        updateListWithDB();
        Log.d(TAG, "Adapter attached");

        ImageDownloadTask t = new ImageDownloadTask(adapter);
        t.execute(movies);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Movie movie = movies.get(position);

                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("id", movie.getId());
                i.putExtra("imageURL", movie.getImageURL());
                i.putExtra("image", movie.getImage());
                i.putExtra("title", movie.getTitle());
                i.putExtra("release", movie.getRelease());
                i.putExtra("overview", movie.getOverview());
                i.putExtra("stored", movie.isStored());
                startActivityForResult(i, 1);
            }
        });
    }


}



