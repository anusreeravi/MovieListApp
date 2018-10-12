package com.sky.movielistapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sky.movielistapp.movielist.MovieListActivity;
import com.sky.movielistapp.movielist.MovieListAdapter;
import com.sky.movielistapp.movielist.MovieListFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

//Test UI class for Espresso Tests
@RunWith(AndroidJUnit4.class)
public class TestMovieListFragment
         {
    @Rule
    public ActivityTestRule<MovieListActivity> mActivityRule =
            new ActivityTestRule<>(MovieListActivity.class);

    @Test
    public void instantiateFragment() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setupFragment();
            }
        });

    }

    private MovieListFragment setupFragment() {
        MovieListActivity activity =  mActivityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        MovieListFragment movieListFragment = new MovieListFragment();
        transaction.add(R.id.fragment_container, movieListFragment);
        transaction.commit();
        return movieListFragment;
    }

    @Test
    public void pageLoaded()
    {
        //launchActivityWithIntent();
        onView(withId(R.id.searchView))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movie_list_recyclerview))
                .check(matches(isDisplayed()));

        }

    private void launchActivityWithIntent()
    {
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
    }


    @Test
    public void testMovieList() {
        onView(ViewMatchers.withId(R.id.movie_list_recyclerview)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_list_recyclerview))
                .perform(RecyclerViewActions.actionOnHolderItem(containsItemTitle("Dun"), click()));
    }

   /* @Test
    public void testSearchList() {
        onView(withId(R.id.searchView)).perform(click());
                 // Type the text in the search view and submit the query
        onView(withId(R.id.searchView))
                         .check(matches(isDisplayed()));
        onView(withId(R.id.searchView)).perform(typeText("Welcome"));
        onView(ViewMatchers.withId(R.id.movie_list_recyclerview)).check(matches(isDisplayed()));
        onView(withId(R.id.movie_list_recyclerview)).perform(RecyclerViewActions.actionOnHolderItem(containsItemTitle("Welcome"), click()));
    }*/

    private static Matcher<RecyclerView.ViewHolder> containsItemTitle(final String title) {
        Checks.checkNotNull(title);
        return new BoundedMatcher<RecyclerView.ViewHolder, MovieListAdapter.MovieViewHolder>(
                MovieListAdapter.MovieViewHolder.class) {

            @Override
            protected boolean matchesSafely(MovieListAdapter.MovieViewHolder viewHolder) {
                TextView titleTextView = viewHolder.itemView.findViewById(R.id.title);
                return ((titleTextView.getText().toString().contains(title))
                        && (titleTextView.getVisibility() == View.VISIBLE));
                }

                @Override
                public void describeTo(Description description) {
                description.appendText(title);
                }
                };
        }

}
