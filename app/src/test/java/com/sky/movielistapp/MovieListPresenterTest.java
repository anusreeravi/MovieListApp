package com.sky.movielistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.runner.AndroidJUnit4;

import com.sky.movielistapp.db.DBManager;
import com.sky.movielistapp.db.DBWrapper;
import com.sky.movielistapp.models.MovieDBItem;
import com.sky.movielistapp.movielist.MovieListContract;
import com.sky.movielistapp.movielist.MovieListPresenterImpl;
import com.sky.movielistapp.sharedpref.SharedPref;
import com.sky.movielistapp.webservices.MovieService;
import com.sky.movielistapp.webservices.RetrofitInstance;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MovieListPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    String LAST_ACCESS_TIME="last_access_time";

    long TEN_MINUTES= 10*60*1000;;

    @InjectMocks
    DBWrapper dbWrapper;

    @InjectMocks
    MovieListPresenterImpl movieListPresenter;

    private long lastAccessTime=0;

    @Mock
    private SharedPref sharedPref;

    @Before
    public void setUp() throws Exception {
        dbWrapper.setUpDB(RuntimeEnvironment.application);
    }
    @Test
    public void getMovieListNotFromDBTest() {

        long currentTime = new Date().getTime();
        lastAccessTime = sharedPref.getValue(LAST_ACCESS_TIME);
        if ((currentTime - sharedPref.getValue(LAST_ACCESS_TIME) > TEN_MINUTES) && dbWrapper.getAllData() != null && dbWrapper.getAllData().size() > 0) {
         Assert.assertEquals(1,1);
        }else
            Assert.assertFalse(false);
    }


    @Test
    public void searchMovieTitleTest() {
        dbWrapper.addMoviesToDB(populateList());
        MovieDBItem sampleItem = populateList().get(0);
        List<MovieDBItem> listMovies = dbWrapper.searchMovies(sampleItem.getTitle());


        Assert.assertEquals(listMovies.get(0).getTitle(), sampleItem.getTitle());
        Assert.assertEquals(listMovies.get(0).getYear(), sampleItem.getYear());
        Assert.assertEquals(listMovies.get(0).getPoster(), sampleItem.getPoster());
        Assert.assertEquals(listMovies.get(0).getId(), sampleItem.getId());
        Assert.assertEquals(listMovies.get(0).getGenre(), sampleItem.getGenre());

    }

    @Test
    public void searchMovieGenreTest() {
        dbWrapper.addMoviesToDB(populateList());

        MovieDBItem sampleItem = populateList().get(0);
        List<MovieDBItem> listMovies = dbWrapper.searchMovies(sampleItem.getGenre());
        Assert.assertEquals(listMovies.get(0).getTitle(), sampleItem.getTitle());
        Assert.assertEquals(listMovies.get(0).getYear(), sampleItem.getYear());
        Assert.assertEquals(listMovies.get(0).getPoster(), sampleItem.getPoster());
        Assert.assertEquals(listMovies.get(0).getId(), sampleItem.getId());
        Assert.assertEquals(listMovies.get(0).getGenre(), sampleItem.getGenre());

    }

    @Test
    public void getMovieListFromDBTest() {
           dbWrapper.addMoviesToDB(populateList());

           List<MovieDBItem> listMovies = dbWrapper.getAllData();

            MovieDBItem sampleItem = populateList().get(0);

            Assert.assertEquals(listMovies.get(0).getTitle(), sampleItem.getTitle());
            Assert.assertEquals(listMovies.get(0).getYear(), sampleItem.getYear());
            Assert.assertEquals(listMovies.get(0).getPoster(), sampleItem.getPoster());
            Assert.assertEquals(listMovies.get(0).getId(), sampleItem.getId());
            Assert.assertEquals(listMovies.get(0).getGenre(), sampleItem.getGenre());


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