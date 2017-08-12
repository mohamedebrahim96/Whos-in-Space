package com.vacuum.app.whoisinspace.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vacuum.app.whoisinspace.Model.Astronaut;
import com.vacuum.app.whoisinspace.Model.AstronautAdapter2;
import com.vacuum.app.whoisinspace.Model.Person;
import com.vacuum.app.whoisinspace.R;
import com.vacuum.app.whoisinspace.Retrofit.ApiClient;
import com.vacuum.app.whoisinspace.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
public class FragmentOne extends Fragment implements ScreenShotable {


    public static final String BASE_URL = "http://api.open-notify.org/";
    private View Fragmentone_view;
    private Bitmap bitmap;
    RecyclerView recyclerView;
    LayoutInflater inflater2;
    public List poster;
    public List extract_list;
    public List<Person> astronauts;

    public static FragmentOne newInstance() {
        FragmentOne fragmentOne = new FragmentOne();
        return fragmentOne;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);

        inflater2 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        recyclerView = rootView.findViewById(R.id.card_recycler_view);
        ApiInterface apiService =
                ApiClient.getClient(BASE_URL).create(ApiInterface.class);

        Call<Astronaut> call = apiService.getAstronaut2("http://api.open-notify.org/astros.json");
        call.enqueue(new Callback<Astronaut>() {
            @Override
            public void onResponse(Call<Astronaut> call, Response<Astronaut> response) {
                astronauts = response.body().getPeople();
                Log.d("Sucess", "Number of movies received: " + astronauts.size());
                //LinearLayoutManager llm = new LinearLayoutManager(getContext());
                //llm.setOrientation(LinearLayoutManager.VERTICAL);
                //recyclerView.setLayoutManager(llm);
                //recyclerView.setAdapter(new GridAdapter(astronauts, getActivity()));
                AstronautAdapter2 adapter2 = new AstronautAdapter2(astronauts, getActivity());

                adapter2.createAdapter(recyclerView);

            }

            @Override
            public void onFailure(Call<Astronaut> call, Throwable t) {
                // Log error here since request failed
                Log.e("Error", t.toString());
            }
        });
        return rootView;
    }



    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(Fragmentone_view.getWidth(),
                        Fragmentone_view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Fragmentone_view.draw(canvas);
                FragmentOne.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.Fragmentone_view = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}