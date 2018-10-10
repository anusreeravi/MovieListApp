package com.sky.movielistapp.app;


import com.sky.movielistapp.movielist.MovieListFragment;
import com.sky.movielistapp.sharedpref.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MovieAppModule.class,SharedPrefModule.class})
public interface MovieAppComponent {
       void inject(MovieListFragment movieListFragment);
//       void inject(MovieListPresenterImpl presenter);

}
