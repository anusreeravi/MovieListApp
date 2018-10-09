package com.sky.movielistapp.webservices;

import com.sky.movielistapp.models.MovieListItem;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
/**
 * Created by Anu on 09/10/2018.
 */

public interface MovieApi {

        @GET("movies/")
        Observable<MovieListItem> getMovieList();

        @GET
        Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

}

