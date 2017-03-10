package com.tomer.fadingtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Tomer Rosenfeld aka rosenpin
 *         Created by rosenpin on 12/8/16.
 */

public class FadingTextView extends android.support.v7.widget.AppCompatTextView {

    public static final int DEFAULT_TIME_OUT = 15000;
    public static final int MILLISECONDS = 1,
            SECONDS = 2,
            MINUTES = 3;

    private Animation fadeInAnimation, fadeOutAnimation;
    private Handler handler;
    private CharSequence[] texts;
    private boolean isShown;
    private int position;
    private int timeout = DEFAULT_TIME_OUT;

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
        super(context, attrs, defStyleAttr);
        init(context);
        handleAttrs(context, attrs);
    }

    /**
     * Resuming the animation
     * Should only be used if you notice {@link #onAttachedToWindow()} ()} is not being executed as expected
     */
    public void resume() {
        isShown = true;
        startAnimation();
    }

    /**
     * Pausing the animation
     * Should only be used if you notice {@link #onDetachedFromWindow()} is not being executed as expected
     */
    public void pause() {
        isShown = false;
        stopAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resume();
    }

    private void init(Context context) {
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fadein);
        fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fadeout);
        handler = new Handler();
        isShown = true;
    }

    private void handleAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.fading_text_view);
        this.texts = a.getTextArray(R.styleable.fading_text_view_texts);
        this.timeout = Math.abs(a.getInteger(R.styleable.fading_text_view_timeout, 14500)) + getResources().getInteger(android.R.integer.config_longAnimTime);
        a.recycle();
    }

    public CharSequence[] getTexts() {
        return texts;
    }

    /**
     * Sets the texts to be shuffled using a string array
     *
     * @param texts The string array to use for the texts
     */
    public void setTexts(@NonNull String[] texts) {
        if (texts.length < 1)
            throw new IllegalArgumentException("There must be at least one text");
        else {
            this.texts = texts;
            stopAnimation();
            position = 0;
            startAnimation();
        }
    }

    /**
     * Sets the texts to be shuffled using a string array resource
     *
     * @param texts The string array resource to use for the texts
     */
    public void setTexts(@ArrayRes int texts) {
        if (getResources().getStringArray(texts).length < 1)
            throw new IllegalArgumentException("There must be at least one text");
        else {
            this.texts = getResources().getStringArray(texts);
            stopAnimation();
            position = 0;
            startAnimation();
        }
    }

    /**
     * This method should only be used to forcefully apply timeout changes
     * It will dismiss the currently queued animation change and start a new animation
     */
    public void forceRefresh() {
        stopAnimation();
        startAnimation();
    }

    /**
     * Sets the length of time to wait between text changes in milliseconds
     *
     * @param timeout The length of time to wait between text change in milliseconds
     * @deprecated use {@link #setTimeout(double, int)} instead.
     */
    @Deprecated
    public void setTimeout(int timeout) {
        if (timeout < 1)
            throw new IllegalArgumentException("Timeout must be longer than 0");
        else
            this.timeout = timeout;
    }

    /**
     * Sets the length of time to wait between text changes in specific time units
     *
     * @param timeout  The length of time to wait between text change
     * @param timeUnit The time unit to use for the timeout parameter
     *                 Must be of {@link TimeUnit} type.    Either {@link #MILLISECONDS} or
     *                 {@link #SECONDS} or
     *                 {@link #MINUTES}
     */
    public void setTimeout(double timeout, @TimeUnit int timeUnit) {
        if (timeout <= 0)
            throw new IllegalArgumentException("Timeout must be longer than 0");
        else {
            int multiplier;
            switch (timeUnit) {
                case MILLISECONDS:
                    multiplier = 1;
                    break;
                case SECONDS:
                    multiplier = 1000;
                    break;
                case MINUTES:
                    multiplier = 60000;
                    break;
                default:
                    multiplier = 1;
                    break;
            }
            this.timeout = (int) (timeout * multiplier);
        }
    }

    private void stopAnimation() {
        handler.removeCallbacksAndMessages(null);
        if (getAnimation() != null) getAnimation().cancel();
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

    @IntDef({MILLISECONDS, SECONDS, MINUTES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeUnit {
    }
}
