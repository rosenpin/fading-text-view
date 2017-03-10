package com.tomerrosenfeld.fadingtextview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tomer.fadingtextview.FadingTextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Random;

import static com.tomer.fadingtextview.FadingTextView.SECONDS;

public class MainActivity extends AppCompatActivity {

    int[] jokes = {R.array.examples_1, R.array.examples_2, R.array.examples_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FadingTextView related code
        final FadingTextView fadingTextView = (FadingTextView) findViewById(R.id.fadingTextView);
        fadingTextView.setTimeout(2, SECONDS);
        //Setting up the timeout seek bar
        DiscreteSeekBar seekBar = (DiscreteSeekBar) findViewById(R.id.timeout_bar);
        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                fadingTextView.setTimeout(value, SECONDS);
                fadingTextView.forceRefresh();
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });
        //Setting up the Github Floating Action Button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/rosenpin/FadingTextView"));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);
            }
        });
        //Show jokes if the app is in production
        if (!BuildConfig.DEBUG) {
            fadingTextView.setTexts(jokes[new Random().nextInt((3) + 1)]);
        }
    }
}
