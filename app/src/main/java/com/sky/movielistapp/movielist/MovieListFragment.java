package com.sky.movielistapp.movielist;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.movielistapp.R;
import com.sky.movielistapp.app.DaggerMovieAppComponent;
import com.sky.movielistapp.app.MovieAppModule;
import com.sky.movielistapp.models.MovieDBItem;
import com.sky.movielistapp.sharedpref.SharedPrefModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
Fragment class where movie list is populated
 */
public class MovieListFragment extends Fragment implements MovieListContract.View {
    private final int GRID_PORTRAIT_COLUMN = 3;
    private final int GRID_LANDSCAPE_COLUMN = 5;
    private final String MOVIE_ID = "movie_id";
    private final String LOADING = "Loading...";
    private Unbinder unbinder;
    private GridLayoutManager layoutManager;
    private ProgressDialog progressDialog;
    @BindView(R.id.movie_list_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;
    private MovieListAdapter movieListAdapter;
    @Inject
    MovieListContract.Presenter movieListPresenter;

    private int columns=GRID_PORTRAIT_COLUMN;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         DaggerMovieAppComponent.builder()
                .movieAppModule(new MovieAppModule(this))
                 .sharedPrefModule(new SharedPrefModule())
                .build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movielist, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(LOADING);
        progressDialog.setCancelable(false);
        inflateViews();
        return rootView;
    }



    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Tells presenter that screen is loaded to show content
        movieListPresenter.onPageLoaded();
    }

    @Override
    public void showMovies(List<MovieDBItem> movies) {
        populateList(movies);

    }

    /*
     Binding view with adapter
     */
    private void populateList(List<MovieDBItem> movieList) {
        if (movieListAdapter == null) {
            movieListAdapter = new MovieListAdapter(movieList,movieListPresenter);
            recyclerView.setAdapter(movieListAdapter);
        }
        movieListAdapter.setMovieList(movieList);
        movieListAdapter.notifyDataSetChanged();
    }



    @Override
    public void showProgress() {
        //Can be used to show loading progress
        if(progressDialog!=null)
            progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        //can be used to dismiss any existing progress dialog
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        columns = GRID_LANDSCAPE_COLUMN;
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            columns = GRID_PORTRAIT_COLUMN;
        layoutManager
                = new GridLayoutManager(getActivity(), columns, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        super.onConfigurationChanged(newConfig);



    }
/*
Grid layout with Recyclerview for grid style
 */
    private void inflateViews(){
        progressDialog = new ProgressDialog(getActivity());

        layoutManager
                = new GridLayoutManager(getActivity(), columns, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setUpItemTouchHelper();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListPresenter.onSearchMovie(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieListPresenter.onSearchMovie(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                movieListPresenter.onPageLoaded();
                return false;
            }
        });
    }


    private void setUpItemTouchHelper() {

        ItemTouchHelper SimpleItemTouchHelperCallback = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });
        SimpleItemTouchHelperCallback.attachToRecyclerView(recyclerView);
    }


}
