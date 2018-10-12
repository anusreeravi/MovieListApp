package com.sky.movielistapp.webservices;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Anu on 09/10/2018.
 */

public class RetrofitInstance {

    //BASE URL for API
    private static String BASE_URL="https://movies-sample.herokuapp.com/api/";;

    public static Retrofit.Builder retroBuilder = null;


    public  RetrofitInstance() {

        retroBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHeaderIntercepter());
    }


    /*
    Headers for JSON Response
     */
    private static OkHttpClient getHeaderIntercepter() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder reqBuilder = original.newBuilder().header("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .method(original.method(), original.body());
                Request request = reqBuilder.build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient.addInterceptor(loggingInterceptor);
        OkHttpClient httpClient = okHttpClient.readTimeout(200, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();
        return httpClient;
    }



}
