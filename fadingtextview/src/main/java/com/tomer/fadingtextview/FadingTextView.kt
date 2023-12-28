package com.tomer.fadingtextview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.ArrayRes
import androidx.appcompat.widget.AppCompatTextView
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

/**
 * @author Tomer Rosenfeld AKA rosenpin
 * Created by rosenpin on 12/8/16.
 */
class FadingTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val fadeInAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.fadein
        )
    }
    private val fadeOutAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.fadeout
        )
    }
    private val handler: Handler = Handler(Looper.getMainLooper())

    var texts: Array<CharSequence> = emptyArray()
        private set

    private var isShown = true
    private var position = 0
    private var timeout = DEFAULT_TIME_OUT
    private var stopped = false

    init {
        handleAttrs(attrs)
    }

    /**
     * Resumes the animation
     * Should only be used if you notice @see [onAttachedToWindow]} is not being executed as expected
     */
    fun resume() {
        isShown = true
        startAnimation()
    }

    /**
     * Pauses the animation
     * Should only be used if you notice @see [onDetachedFromWindow] is not being executed as expected
     */
    fun pause() {
        isShown = false
        stopAnimation()
    }

    /**
     * Stops the animation
     * Unlike the pause function, the stop method will permanently stop the animation until the view is restarted
     */
    fun stop() {
        isShown = false
        stopped = true
        stopAnimation()
    }

    /**
     * Restarts the animation
     * Only use this to restart the animation after stopping it using {@see [stop]}
     */
    fun restart() {
        isShown = true
        stopped = false
        startAnimation()
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        pause()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        resume()
    }

    /**
     * Handle the xml attributes
     * set the texts
     * set the timeout
     *
     * @param attrs provided attributes
     */
    private fun handleAttrs(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.FadingTextView)

            typedArray.getTextArray(R.styleable.FadingTextView_fadingTextViewTexts)?.let { textArray ->
                texts = textArray
            }

            val baseTimeout = typedArray.getInteger(
                R.styleable.FadingTextView_fadingTextViewTimeout,
                DEFAULT_TIME_OUT.toInt(DurationUnit.MILLISECONDS)
            ).milliseconds
            val animationDuration =
                resources.getInteger(android.R.integer.config_longAnimTime).milliseconds

            timeout = baseTimeout + animationDuration

            typedArray.getBoolean(R.styleable.FadingTextView_fadingTextViewShuffle, false).also { shouldShuffle ->
                if (shouldShuffle) {
                    shuffle()
                }
            }
            typedArray.recycle()
        }
    }

    /**
     * Sets the texts to be shuffled using a string array
     *
     * @param texts The string array to use for the texts
     */
    fun setTexts(texts: Array<String>) {
        require(texts.isNotEmpty()) { "There must be at least one text" }
        this.texts = texts.map { it }.toTypedArray()
        stopAnimation()
        position = 0
        startAnimation()
    }

    /**
     * Sets the texts to be shuffled using a string array resource
     *
     * @param texts The string array resource to use for the texts
     */
    fun setTexts(@ArrayRes texts: Int) {
        val mTexts = resources.getStringArray(texts)
        setTexts(mTexts)
    }

    /**
     * This method should only be used to forcefully apply timeout changes
     * It will dismiss the currently queued animation change and start a new animation
     */
    fun forceRefresh() {
        stopAnimation()
        startAnimation()
    }

    /**
     * Fades text to position in provided array and pauses
     * Consider calling pause() method before calling this function to avoid overriding currently active animation
     */
    fun fadeTo(position: Int) {
        this.position = position
        isShown = true
        startAnimation()
        pause()
    }

    /**
     * Shuffle the strings
     * Each time this method is ran, the order of the strings will be randomized
     * After you set texts dynamically you will have to call shuffle again
     *
     * @throws IllegalArgumentException if you don't supply texts to the FadingTextView in your XML file. You can leave it empty by using FTV.placeholder and set it manually later using the setTexts method
     */
    fun shuffle() {
        require(texts.isNotEmpty()) { "You must provide a string array to the FadingTextView using the texts parameter" }
        val textsList = texts.toMutableList()
        textsList.shuffle()
        this.texts = textsList.toTypedArray()
    }

    /**
     * Sets the length of time to wait between text changes in specific time units
     *
     * @param timeout  The duration to wait between text changes
     * @throws IllegalArgumentException if the duration is not positive.
     */
    fun setTimeout(timeout: Duration) {
        require(timeout.isPositive()) { "Timeout must be longer than 0" }
        this.timeout = timeout
    }

    /**
     * This method is overridden to prevent animations from starting when the view is not shown
     * If you want to start the animation manually, use the @see [resume] method
     * @param animation the animation to start now
     */
    override fun startAnimation(animation: Animation) {
        if (isShown && !stopped) {
            super.startAnimation(animation)
        }
    }

    /**
     * Start the animation
     */
    private fun startAnimation() {
        if (isInEditMode || texts.isEmpty()) return
        text = texts[position]
        startAnimation(fadeInAnimation)
        handler.postDelayed({
            startAnimation(fadeOutAnimation)
            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    if (isShown) {
                        position = if (position == texts.size - 1) 0 else position + 1
                        startAnimation()
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }, timeout.inWholeMilliseconds)
    }

    /**
     * Stop the currently active animation
     */
    private fun stopAnimation() {
        handler.removeCallbacksAndMessages(null)
        animation?.cancel()
    }

    companion object {
        private val DEFAULT_TIME_OUT = 15.seconds
    }
}
