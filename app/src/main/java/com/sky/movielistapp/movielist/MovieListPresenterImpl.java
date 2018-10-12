package com.sky.movielistapp.movielist;


import android.os.Environment;
import android.util.Log;

import com.sky.movielistapp.db.DBWrapper;
import com.sky.movielistapp.models.MovieDBItem;
import com.sky.movielistapp.models.MovieListItem;
import com.sky.movielistapp.sharedpref.SharedPref;
import com.sky.movielistapp.webservices.MovieService;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
Presenter class for fetching movie list  and updating View
 */
public class MovieListPresenterImpl implements MovieListContract.Presenter {

    public static long TEN_MINUTES = 10*60*1000;
    private static String LAST_ACCESS_TIME="last_access_time";
    private List<MovieDBItem> listMovies;
    private MovieService movieService;
    private long lastAccessTime=0;
    private DBWrapper dbWrapper;
    private SharedPref sharedPref;
    private MovieListContract.View moviesView;

    @Inject
    public MovieListPresenterImpl(SharedPref sharedPref, MovieListContract.View view,
                                  DBWrapper dbManager, MovieService movieService){
        this.sharedPref = sharedPref;
        this.moviesView = view;
        this.dbWrapper = dbManager;
        this.movieService = movieService;
    }

    @Override
    public void onPageLoaded() {
        moviesView.showProgress();
        long currentTime = new Date().getTime();
        lastAccessTime = sharedPref.getValue(LAST_ACCESS_TIME);
        if((currentTime-sharedPref.getValue(LAST_ACCESS_TIME)<TEN_MINUTES) && dbWrapper.getAllData()!=null && dbWrapper.getAllData().size()>0) {
            lastAccessTime = currentTime;
            sharedPref.putValue(LAST_ACCESS_TIME,lastAccessTime);
            getMoviesFromDB();

        }
        else{
            lastAccessTime = currentTime;
            sharedPref.putValue(LAST_ACCESS_TIME,lastAccessTime);
            fetchMoviesInfo();
        }
    }


    @Override
    public void onSearchMovie(String query) {
        //Searching with query from DB
       searchMoviesInfo(query);
    }
    /**
     * Fetching movie from API
     */

    public void fetchMoviesInfo() {
       movieService.getMovieList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieListItem>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(MovieListItem movieDetails) {
                if(movieDetails!=null)
                listMovies = movieDetails.getData();
                //Saving data to DB
                if(listMovies!=null && listMovies.size()>0) {
                    dbWrapper.deleteMoviesFromDB();
                    dbWrapper.addMoviesToDB(listMovies);
                    String filePath = Environment.getExternalStorageDirectory().toString() + "/com/movielist";
                    if(new File(filePath).exists())
                        new File(filePath).delete();
                }
                moviesView.showMovies(listMovies);
                moviesView.dismissProgress();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Networkcall",e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("Networkcall","Completed");

            }
        });

    }

    //Fetching from DB
    private void getMoviesFromDB() {
        listMovies= dbWrapper.getAllData();
        moviesView.showMovies(listMovies);
        moviesView.dismissProgress();
    }

    //Searching DB
    private void searchMoviesInfo(String query) {
        listMovies= dbWrapper.searchMovies(query);
        moviesView.showMovies(listMovies);
        moviesView.dismissProgress();
    }
}
