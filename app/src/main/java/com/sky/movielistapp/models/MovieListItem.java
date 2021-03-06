package com.sky.movielistapp.models;

/**
 * Created by Anu on 09/10/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Model class for API
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



