package com.vacuum.app.whoisinspace.Fragment;

/**
 * Created by Home on 2017-07-28.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vacuum.app.whoisinspace.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class AboutFragment extends Fragment implements ScreenShotable {

    private View Fragmentone_view;
    private Bitmap bitmap;

    public static AboutFragment newInstance() {
        AboutFragment aboutFragment = new AboutFragment();

        return aboutFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_fragment, container, false);
        TextView textView1 = rootView.findViewById(R.id.text1);
        TextView textView2 = rootView.findViewById(R.id.text2);
        Button button1 = rootView.findViewById(R.id.Button01);
        Button button2 = rootView.findViewById(R.id.Button02);
        Button button3 = rootView.findViewById(R.id.Button03);
        Typeface typeface = Typeface.createFromAsset(rootView.getContext().getAssets(),"fonts/oswaldlight.ttf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        Html.fromHtml(getString(R.string.ss));


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/mohamedebrahim93");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://twitter.com/mohamedhima96");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.ebrahimm131@gmail.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
                AboutFragment.this.bitmap = bitmap;
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
