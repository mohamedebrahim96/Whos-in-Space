package com.vacuum.app.whoisinspace.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.vacuum.app.whoisinspace.Fragment.DetailsActivity;
import com.vacuum.app.whoisinspace.Model.Person;
import com.vacuum.app.whoisinspace.Model.Poster;
import com.vacuum.app.whoisinspace.R;
import com.vacuum.app.whoisinspace.Retrofit.ApiClient;
import com.vacuum.app.whoisinspace.Retrofit.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 2017-08-04.
 */

public class AstronautAdapter2  {

    private List<Person> person;
    public Context context;
    LayoutInflater inflater2;
    ApiInterface apiService;
    List<Poster> poster ;

    public static int x;
    public AstronautAdapter2(List<Person> person, Context context) {
        this.person = person;
        this.context = context;
        inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void createAdapter(RecyclerView recyclerView) {
        poster= new LinkedList<Poster>();
        final ParallaxRecyclerAdapter<Person> adapter = new ParallaxRecyclerAdapter<Person>(person) {
            @Override
            public void onBindViewHolderImpl(final RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<Person> adapter, final int position) {
                ((ViewHolder) viewHolder).title.setText(person.get(position).getName());


                apiService =
                        ApiClient.getClient(getUrl(person.get(position).getName())).create(ApiInterface.class);
                Call<ResponseBody> call = apiService.getAstronaut3(getUrl(person.get(position).getName()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String astronauts = null;

                        final int final_position = position;

                        try {
                            astronauts = response.body().string();
                            JSONObject jObject = new JSONObject(astronauts);
                            JSONObject query = jObject.getJSONObject("query");
                            JSONObject pages = query.getJSONObject("pages");
                            Iterator keys = pages.keys();
                            String id1 = keys.next().toString();
                            JSONObject id = pages.getJSONObject(id1);
                            String extract = id.getString("extract");
                            JSONObject thumbnail = id.getJSONObject("thumbnail");
                            String source = thumbnail.getString("source");
                            String title = id.getString("title");

                            ((ViewHolder) viewHolder).description.setText(extract);
                            String replacedString = source.replace("px", "0px");


                            //poster.add(final_position,replacedString);
                            Poster b1=new Poster(position,replacedString);
                            poster.add(b1);

                            Glide.with(context.getApplicationContext()).load(replacedString).apply(RequestOptions.circleCropTransform()).into(((ViewHolder) viewHolder).image);


                            Log.d("replacedString", "Number of title: " + replacedString);
                            Log.d("title", "Number of title: " + title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.d("Sucess", "Number of movies received: " + astronauts);


                    }

                    @Override
                    public void onFailure(Call<ResponseBody>call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error", t.toString());
                    }
                });


                ((ViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("title", ((ViewHolder) viewHolder).title.getText().toString());
                        intent.putExtra("description", ((ViewHolder) viewHolder).description.getText().toString());
                        for (Poster string : poster) {
                            if(string.poster2.contains(person.get(position).getName().substring(0,4))){
                                Log.d("Title", "\n\n\n\n the url " + string.poster2);
                                intent.putExtra("poster", string.poster2);
                            }
                        }

                        context.startActivity(intent);
                    }
                });

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<Person> adapter, int i) {
                return new ViewHolder(inflater2.inflate(R.layout.astronaut_layout, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<Person> adapter) {
                return person.size();
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        View header = inflater2.inflate(R.layout.header, recyclerView, false);
        adapter.setParallaxHeader(header, recyclerView);
        adapter.setData(person);
        recyclerView.setAdapter(adapter);


    }



    public String getUrl(String x)
    {
        String m = x;
        if(x.equals("Sergey Ryazanskiy"))
        {
            m = "Sergey Ryazansky";
        }
        String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&titles="+m+"&redirects=true&prop=pageimages|extracts&explaintext";
        return url;
    }




    class ViewHolder extends RecyclerView.ViewHolder {
    public TextView title,description;
       ImageView image;
    public ViewHolder(View v) {
        super(v);
        title = (TextView) v.findViewById(R.id.person);
        image = (ImageView) v.findViewById(R.id.image);
        description = (TextView) v.findViewById(R.id.description);
    }
}
}
