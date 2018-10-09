package com.sky.movielistapp;

import com.sky.movielistapp.models.MovieListItem;
import com.sky.movielistapp.webservices.MovieApi;
import com.sky.movielistapp.webservices.MovieService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.observers.TestObserver;


public class MovieServiceTest {
    MovieApi movieApi;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @InjectMocks
    MovieService movieService;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test public void getMovieListTest() {
        TestObserver<MovieListItem> testObserver =
                movieService.getMovieList().test();
        // given
        testObserver.assertNoErrors();
        testObserver.assertComplete();


    }
}