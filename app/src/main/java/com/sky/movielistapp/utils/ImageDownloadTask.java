package com.sky.movielistapp.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
            saveImage(params[0], bm);
            return bm;
        } catch (Exception e) {
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

     private void saveImage(String path, Bitmap bitmap) {
        try {
//            String filePath = Environment.getExternalStorageDirectory().toString() + "/com/movielist/";
            File pathDir = new File(Environment.getExternalStorageDirectory(), "movielist");
            boolean isPathCreated = pathDir.mkdir();
            String fName= path.substring(path.lastIndexOf("/"));
            if(!(fName.endsWith(".jpg")||fName.endsWith(".jpeg")))
                fName = fName+".jpg";
            File image = new File(pathDir,fName);
            if (image.exists ()) image.delete ();

            // Encode the file as a PNG image.
            FileOutputStream outStream;
            try {

                outStream = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
                /* 100 to keep full quality of the image */

                outStream.flush();
                outStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}