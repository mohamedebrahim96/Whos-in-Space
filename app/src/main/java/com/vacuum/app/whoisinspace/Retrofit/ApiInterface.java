package com.vacuum.app.whoisinspace.Retrofit;

import com.vacuum.app.whoisinspace.Model.Astronaut;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Home on 2017-08-01.
 */

public interface ApiInterface {
    @GET("astros.json")
    Call<Astronaut> getAstronaut1();

    @GET("{fullUrl}")
    Call<Astronaut> getAstronaut2(@Path(value = "fullUrl", encoded = true) String fullUrl);

    @GET
    Call<ResponseBody> getAstronaut3(@Url String url);

    /*@GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key")String*/
}
