package com.sky.movielistapp.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;

/*
 Downloading image and adding bitmap to imageview
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView posterView;

    public ImageDownloadTask(ImageView view) {
        posterView = view;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            Bitmap bm = ImageUtils.getImage(params[0]);
            return bm;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            posterView.setImageBitmap(bitmap);
        }
    }
}