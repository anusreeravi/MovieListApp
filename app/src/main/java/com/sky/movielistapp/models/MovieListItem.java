package com.sky.movielistapp.models;

/**
 * Created by Anu on 09/10/2018.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieListItem {

        @SerializedName("data")
        @Expose
        private List<MovieDBItem> data = null;

        public List<MovieDBItem> getData() {
            return data;
        }

        public void setData(List<MovieDBItem> data) {
            this.data = data;
        }

    }



