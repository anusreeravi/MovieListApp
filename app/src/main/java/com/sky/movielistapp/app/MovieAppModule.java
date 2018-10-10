package com.sky.movielistapp.app;


import android.content.Context;

import com.sky.movielistapp.db.DBManager;
import com.sky.movielistapp.movielist.MovieListContract;
import com.sky.movielistapp.movielist.MovieListPresenterImpl;
import com.sky.movielistapp.sharedpref.SharedPref;
import com.sky.movielistapp.webservices.MovieService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 *
 */
@Module
public  class MovieAppModule {

    private MovieListContract.View view;

    public MovieAppModule(MovieListContract.View view) {
        this.view = view;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return MovieApp.getInstance().getApplicationContext();
    }

    @Singleton
    @Provides
    public MovieListContract.Presenter providesMoviePresenter(SharedPref pref,
                                                              DBManager dbM, MovieService service) {
        return new MovieListPresenterImpl(pref, view, dbM, service);
    }
}