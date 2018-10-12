package com.sky.movielistapp.app;


import com.sky.movielistapp.db.DBModule;
import com.sky.movielistapp.movielist.MovieListFragment;
import com.sky.movielistapp.sharedpref.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MovieAppModule.class,SharedPrefModule.class,DBModule.class})
public interface MovieAppComponent {
       void inject(MovieListFragment movieListFragment);

}
