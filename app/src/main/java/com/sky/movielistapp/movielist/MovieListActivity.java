package com.sky.movielistapp.movielist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sky.movielistapp.R;


/*
Activity class where fragments with movie listing is added
 */
public class MovieListActivity extends AppCompatActivity {


    private final int REQUEST_WRITE_STORAGE = 1;

    /*
    Initialising views and adding movie list fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        MovieListFragment listFragment = new MovieListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listFragment).disallowAddToBackStack().commit();
        checkPermission();

    }

    //Checking WRITE Permission for devices
    private void checkPermission(){
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(this,getString(R.string.error_storage),Toast.LENGTH_LONG);

                }
            }
        }

    }

}
