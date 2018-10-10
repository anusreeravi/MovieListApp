package com.sky.movielistapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sky.movielistapp.R;
import com.sky.movielistapp.movielist.MovieListFragment;


/*
Activity class where fragments are added for listing movies or viewing each movie
 */
public class MovieListActivity extends AppCompatActivity {



    /*
    Initialising views and adding movie list fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        MovieListFragment listFragment = new MovieListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listFragment).disallowAddToBackStack().commit();

    }


}
