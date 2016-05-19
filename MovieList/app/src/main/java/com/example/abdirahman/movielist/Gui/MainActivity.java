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
        InitializeTask t = new InitializeTask(this);//Create a task that will fetch the movies from API
        t.execute(new MovieList());//Execute that task
        DAO.createInstance(this);//Create an instance of DAO
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            updateListWithDB();
        }
    }

    private void updateListWithDB() {
        Cursor c = DAO.getInstance().getMovies();//get the cursor to the movies from db
        if (c != null) { // if there are movies in db
            while(c.moveToNext()) {
                int Id = c.getInt(c.getColumnIndex(DB.ID)); //get the id of the movie
                boolean stored = c.getInt(c.getColumnIndex(DB.STORED)) > 0; // get if the movies is stored
                for(Movie m: movies){ //goes through the movies and if the movie is in db than we set it's stored parameter to true
                    if(m.getId() == Id){
                        m.setStored(stored);
                    }
                }
            }
            c.close();
        }
        adapter.updateList(movies);//update the list in the adapter
    }

    //parameter contains list of movies from API
    public void initializeData(final ArrayList<Movie> movies) {
        this.movies = movies; //assign list of movies to local variable
        adapter = new CustomListAdapter(this, this.movies); //create adapter for the listview
        setListAdapter(adapter);// assigns the adapter to listview
        updateListWithDB();//update the list in MainActivity with the data from database
        //at this point we have a list of movie titles without images
        Log.d(TAG, "Adapter attached");

        //here we download images and refresh the list
        ImageDownloadTask t = new ImageDownloadTask(adapter);
        t.execute(movies);

        //assign the onclick listner
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Movie movie = movies.get(position); //fetch the movie from adapter at give position

                //Create an intent to start a new Activity
                //This is an explicit intent.. we say exactly what activity to run.
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                //The values we want the DetailsActivity to know is add to the intent
                i.putExtra("id", movie.getId());
                i.putExtra("imageURL", movie.getImageURL());
                i.putExtra("image", movie.getImage());
                i.putExtra("title", movie.getTitle());
                i.putExtra("release", movie.getRelease());
                i.putExtra("overview", movie.getOverview());
                i.putExtra("stored", movie.isStored());
                startActivityForResult(i, 1);//send the intent
            }
        });
    }


}



