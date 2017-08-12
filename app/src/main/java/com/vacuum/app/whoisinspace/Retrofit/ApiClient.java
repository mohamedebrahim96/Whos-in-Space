package com.vacuum.app.whoisinspace.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home on 2017-08-01.
 */

public class ApiClient {

    //public static final String BASE_URL = "http://api.open-notify.org/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(String BASE_URL) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
