package com.tomer.fadingtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by tomer on 12/8/16.
 */

public class FadingTextView extends TextView {
    private Animation fadeInAnimation, fadeOutAnimation;
    private Handler handler;
    private CharSequence[] texts;
    private boolean isShown;
    private int position;
    private int timeout = 15000;

    public FadingTextView(Context context) {
        super(context);
        init(context);
    }

    public FadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        handleAttrs(context, attrs);
    }

    public FadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        handleAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FadingTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        handleAttrs(context, attrs);
    }

    public void resume() {
        isShown = true;
        startAnimation();
    }

    public void pause() {
        isShown = false;
        handler.removeCallbacksAndMessages(null);
    }

    private void init(Context context) {
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fadein);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fadeout);
        handler = new Handler();
        isShown = true;
        setMaxLines(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(android.R.style.TextAppearance_Material_Button);
            setTextColor(ContextCompat.getColor(context, android.R.color.white));
        }
    }

    private void handleAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.fading_text_view);
        this.texts = a.getTextArray(R.styleable.fading_text_view_texts);
        this.timeout = Math.abs(a.getInteger(R.styleable.fading_text_view_timeout, 14500)) + getResources().getInteger(android.R.integer.config_longAnimTime);
        a.recycle();
        startAnimation();
    }

    public CharSequence[] getTexts() {
        return texts;
    }

    public void setTexts(@ArrayRes int texts) {
        this.texts = getResources().getStringArray(texts);
    }

    protected void startAnimation() {
        setText(texts[position]);
        startAnimation(fadeInAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation(fadeOutAnimation);
                getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (isShown) {
                            position = position == texts.length - 1 ? 0 : position + 1;
                            startAnimation();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }, timeout);
    }
}
