package com.sky.movielistapp;

import com.sky.movielistapp.models.MovieDBItem;
import com.sky.movielistapp.models.MovieListItem;
import com.sky.movielistapp.webservices.MovieApi;
import com.sky.movielistapp.webservices.MovieService;
import com.sky.movielistapp.webservices.RetrofitInstance;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;

public class MovieServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @InjectMocks
    RetrofitInstance retrofitInstance;

    @InjectMocks
    MovieService movieService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMoviesFromService() {
        TestObserver<MovieListItem> testObserver =
                movieService.getMovieList().test();



        testObserver.assertNoErrors();
        testObserver.assertComplete();

       List<MovieListItem> movieListItems = testObserver.values();
        MovieListItem listItem = movieListItems.get(0);
        MovieDBItem sampleItem = populateList().get(0);

        Assert.assertEquals(listItem.getData().get(0).getTitle(), sampleItem.getTitle());
        Assert.assertEquals(listItem.getData().get(0).getYear(), sampleItem.getYear());
        Assert.assertEquals(listItem.getData().get(0).getPoster(), sampleItem.getPoster());
        Assert.assertEquals(listItem.getData().get(0).getId(), sampleItem.getId());
        Assert.assertEquals(listItem.getData().get(0).getGenre(), sampleItem.getGenre());

        }

    @Test
    public void getMovieImageTest() {
        TestObserver<ResponseBody> testObserver =
                movieService.getImageProfile("https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg").test();
        MovieDBItem sampleItem = populateList().get(0);
        testObserver.assertNoErrors();
        testObserver.assertComplete();

    }

    private List<MovieDBItem> populateList() {
        ArrayList<MovieDBItem> movies = new ArrayList<>();
        try {
            movies.add(new MovieDBItem(912312, "Dunkirk", "2017","History", "https://goo.gl/1zUyyq"));
            movies.add(new MovieDBItem(11241, "Jumanji: welcome to the jungle", "2017","Action" ,"https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg"));
            movies.add(new MovieDBItem(55122, "The Maze Runner", "2014","Action" ,"https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

}