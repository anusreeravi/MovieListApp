package com.sky.movielistapp.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sky.movielistapp.R;
import com.sky.movielistapp.models.MovieDBItem;
import com.sky.movielistapp.utils.ImageDownloadTask;

import java.util.List;


/*
Adapter class for populating  movie list
 */
public class MovieListAdapter extends RecyclerView.Adapter

{
    private List<MovieDBItem> movieList;
    private MovieListContract.Presenter movieListPresenter;

    public MovieListAdapter(List<MovieDBItem> movieList, MovieListContract.Presenter presenter) {
        this.movieList = movieList;
        this.movieListPresenter = presenter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(parent);
    }

    public void setMovieList(List<MovieDBItem> movieList){
        this.movieList = movieList;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MovieViewHolder viewHolder = (MovieViewHolder) holder;
        final MovieDBItem movieItem = movieList.get(position);
        viewHolder.title.setText(movieItem.getTitle());
        viewHolder.genre.setText(movieItem.getGenre());

        viewHolder.year.setText(movieItem.getYear());
        setImagePoster(viewHolder.movieImage, movieItem.getPoster());

    }


    @Override
    public int getItemCount() {

        return movieList.size();
    }


    private class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView year;
        TextView genre;
        RelativeLayout movieLayout;
        ImageView movieImage;
        public MovieViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.title);
            year = (TextView) itemView.findViewById(R.id.year);
            genre = (TextView) itemView.findViewById(R.id.genre);

            movieLayout = (RelativeLayout) itemView.findViewById(R.id.movieLayout);
            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);

        }


    }

    /*
    Downloading Images
     */
    private void setImagePoster(ImageView view, String posterURL) {
       new ImageDownloadTask(view).execute(posterURL);
    }




}