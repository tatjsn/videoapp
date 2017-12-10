package com.example.tat.videoapplication.data.remote;

import com.example.tat.videoapplication.data.model.Video;
import com.example.tat.videoapplication.util.MyTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface VideoService {
    @GET("playlist.json")
    Observable<List<Video>> getVideos();

    class Creator {
        public static VideoService create(String baseUrl) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyTypeAdapterFactory.create())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(VideoService.class);
        }
    }
}
