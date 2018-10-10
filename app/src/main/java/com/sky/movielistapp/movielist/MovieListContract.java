package com.sky.movielistapp.movielist;

import com.sky.movielistapp.models.MovieDBItem;

import java.util.List;

/*
Contract for view listing movies
 */
public interface MovieListContract {

    public interface View {
        void showMovies(List<MovieDBItem> movies);
        void showProgress();
        void dismissProgress();
    }

    public interface Presenter {
        void onPageLoaded();
        void onSearchMovie(String query);
    }
}
