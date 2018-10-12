package com.sky.movielistapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class ImageUtils {

    public interface ImageDownloadListener {
        void onSuccess(Bitmap image);
        void onError(String error);
    }
    public static void getImage(final String imgURL, final ImageDownloadListener listener) {
        new Thread() {
            public void run() {
                URL url = null;
                try {
                    url = new URL(imgURL);
                    Bitmap bm = BitmapFactory.decodeStream(url.openStream());
                    listener.onSuccess(bm);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }

            }
        }.start();

    }

    public static Bitmap getImage(final String imgURL) {
        Bitmap bm=null;
        try {
            URL url = new URL(imgURL);
            bm = BitmapFactory.decodeStream(url.openStream());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return bm;
    }
}
