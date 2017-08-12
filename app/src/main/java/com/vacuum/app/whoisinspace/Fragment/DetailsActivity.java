package com.vacuum.app.whoisinspace.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.vacuum.app.whoisinspace.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 2017-08-05.
 */

public class DetailsActivity extends Activity {
    public RecyclerView recyclerView;
    String title,description,poster;
    TextView title_view,description_view,description_view2;
    ImageView poster_view,header_view;
    List<String> images_list;
    LongOperation task ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_layout);
        recyclerView = findViewById(R.id.card_recycler_view);
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        description = bundle.getString("description");
        poster = bundle.getString("poster");
        task = new LongOperation();
        task.execute(title);
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image =  itemView.findViewById(R.id.image5);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        task.cancel(true);
    }

    private class LongOperation extends AsyncTask<String, Void, List> {

        private Exception exception;
        private ProgressDialog dialog;
        @Override
        protected List<String> doInBackground(String... params) {

            images_list = new ArrayList<>();
            try {
                String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36";
                String googleUrl = "https://www.google.com/search?tbm=isch&q=" + params[0].replace(" ", "+");
                Document doc1 = Jsoup.connect(googleUrl).userAgent(ua).timeout(10 * 1000).get();

                Elements images = doc1.select("[data-src]");
                for (Element image : images) {

                    System.out.println("\nsrc : " + image.attr("abs:data-src"));
                    images_list.add(image.attr("abs:data-src"));
                }



            } catch (Exception e) {
                this.exception = e;
            }
            return images_list;
        }

        @Override
        protected void onPostExecute(final List images_list2) {

            final ParallaxRecyclerAdapter<String> adapter = new ParallaxRecyclerAdapter<String>(images_list2) {
                @Override
                public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
                    //((ViewHolder) viewHolder).textView.setText(adapter.getData().get(i));
                    Glide.with(getApplicationContext())
                            .load(adapter.getData().get(i))
                            .into(((ViewHolder) viewHolder).image);

                }

                @Override
                public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<String> adapter, int i) {
                    return new ViewHolder(getLayoutInflater().inflate(R.layout.grid_view, viewGroup, false));
                }

                @Override
                public int getItemCountImpl(ParallaxRecyclerAdapter<String> adapter) {
                    return images_list2.size();
                }
            };
            adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
                @Override
                public void onClick(View v, int position) {
                    Toast.makeText(getApplicationContext(),   title , Toast.LENGTH_SHORT).show();
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            View header =  getLayoutInflater().inflate(R.layout.header, recyclerView, false);

            title_view = (TextView) header.findViewById(R.id.person);
            poster_view = (ImageView) header.findViewById(R.id.image);
            description_view = (TextView) header.findViewById(R.id.description);
            description_view2 = (TextView) header.findViewById(R.id.description2);

            header_view = (ImageView) header.findViewById(R.id.header);
            header_view.setAlpha(0.5f);
            title_view.setText(title);
            description_view.setText(description);
            description_view2.setText(description);

            Glide.with(header).load(poster).apply(RequestOptions.circleCropTransform()).into(poster_view);


            adapter.setParallaxHeader(header, recyclerView);
            adapter.setData(images_list2);
            recyclerView.setAdapter(adapter);
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            /*dialog = new ProgressDialog(DetailsActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();*/
            dialog= ProgressDialog.show(DetailsActivity.this, "Please wait...","Your connection speed is bad", true);
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                public void onCancel(DialogInterface dialog) {
                    task.cancel(true);
                    finish();
                }
            });
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}

