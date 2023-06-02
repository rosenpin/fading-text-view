package com.tomerrosenfeld.fadingtextview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tomer.fadingtextview.FadingTextView
import org.adw.library.widgets.discreteseekbar.BuildConfig
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import java.util.Random
import kotlin.time.Duration.Companion.seconds

class MainActivity : AppCompatActivity() {
    private val jokes = intArrayOf(R.array.examples_1, R.array.examples_2, R.array.examples_3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        //FadingTextView related code
        val fadingTextView = findViewById<FadingTextView>(R.id.fadingTextView)
        fadingTextView.setTimeout(2.seconds)
        //Setting up the timeout seek bar
        val seekBar = findViewById<DiscreteSeekBar>(R.id.timeout_bar)
        seekBar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(
                seekBar: DiscreteSeekBar,
                value: Int,
                fromUser: Boolean
            ) {
                fadingTextView.setTimeout(value.seconds)
                fadingTextView.forceRefresh()
            }

            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {}
            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {}
        })
        //Setting up the Github Floating Action Button
        findViewById<View>(R.id.fab).setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.github.com/rosenpin/FadingTextView")
            )
            browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(browserIntent)
        }
        //Show jokes if the app is in production
        if (!BuildConfig.DEBUG) {
            fadingTextView.setTexts(jokes[Random().nextInt(3 + 1)])
        }
    }
}