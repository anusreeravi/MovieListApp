package com.sky.movielistapp.webservices;


import com.sky.movielistapp.models.MovieListItem;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MovieService {

    private RetrofitInstance retrofitInstance;
    private MovieApi movieApi;

    @Inject
    public MovieService(){
        movieApi = RetrofitInstance.getInstance().create(MovieApi.class);
    }



     public  Observable<MovieListItem>  getMovieList() {
      return movieApi.getMovieList();
    }


    public Observable<ResponseBody> getImageProfile(String url) {
        return movieApi.downloadFileWithDynamicUrlSync(url);
    }
}
