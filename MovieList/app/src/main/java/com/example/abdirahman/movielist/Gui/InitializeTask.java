package com.example.abdirahman.movielist.Gui;

/* Uses AsyncTask to create a task away from the main UI thread. This task takes a
    URL string and uses it to create an HttpUrlConnection. Once the connection
    has been established, the AsyncTask downloads the contents of the webpage as
    an InputStream. Finally, the InputStream is converted into a string, which is
    displayed in the UI by the AsyncTask's onPostExecute method.*/


import android.os.AsyncTask;

import com.example.abdirahman.movielist.Model.Movie;
import com.example.abdirahman.movielist.MovieDALC.MovieList;

import java.util.ArrayList;



public class InitializeTask extends AsyncTask<

        MovieList, Void, ArrayList<Movie>>
{

        MainActivity m_context;

        public InitializeTask(MainActivity context) {
        m_context = context;
    }

        @Override
        protected ArrayList<Movie> doInBackground(MovieList... ms) {

        ms[0].loadAll();
        return ms[0].getAll();
    }


        @Override
        protected void onPostExecute(final ArrayList<Movie> movies) {
        m_context.initializeData(movies);
    }
    }
