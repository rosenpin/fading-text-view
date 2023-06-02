package com.tomer.fadingtextview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.ArrayRes
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.abs

class FadingTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val fadeInAnimation: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fadein) }
    private val fadeOutAnimation: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fadeout) }
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

    fun resume() {
        isShown = true
        startAnimation()
    }

    fun pause() {
        isShown = false
        stopAnimation()
    }

    fun stop() {
        isShown = false
        stopped = true
        stopAnimation()
    }

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

    private fun handleAttrs(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FadingTextView)
            typedArray.getTextArray(R.styleable.FadingTextView_texts)?.let { textArray ->
                texts = textArray
            }
            timeout = abs(typedArray.getInteger(R.styleable.FadingTextView_timeout, DEFAULT_TIME_OUT)) +
                    resources.getInteger(android.R.integer.config_longAnimTime)
            typedArray.getBoolean(R.styleable.FadingTextView_shuffle, false).also { shuffle ->
                if (shuffle) {
                    shuffleTexts()
                }
            }
            typedArray.recycle()
        }
    }

    fun setTexts(texts: Array<String>) {
        require(texts.isNotEmpty()) { "There must be at least one text" }
        this.texts = texts.map { it }.toTypedArray()
        stopAnimation()
        position = 0
        startAnimation()
    }

    fun setTexts(@ArrayRes texts: Int) {
        val mTexts = resources.getStringArray(texts)
        require(mTexts.isNotEmpty()) { "There must be at least one text" }
        this.texts = mTexts.map { it }.toTypedArray()
        stopAnimation()
        position = 0
        startAnimation()
    }

    fun forceRefresh() {
        stopAnimation()
        startAnimation()
    }

    fun fadeTo(position: Int) {
        this.position = position
        isShown = true
        startAnimation()
        pause()
    }

    fun shuffleTexts() {
        require(texts.isNotEmpty()) { "You must provide a string array to the FadingTextView using the texts parameter" }
        val textsList = texts.toMutableList()
        textsList.shuffle()
        this.texts = textsList.toTypedArray()
    }

    fun setTimeout(timeout: Int) {
        require(timeout >= 1) { "Timeout must be longer than 0" }
        this.timeout = timeout
    }

    fun setTimeout(timeout: Double, @TimeUnit timeUnit: Int) {
        require(!(timeout <= 0)) { "Timeout must be longer than 0" }
        val multiplier: Int = when (timeUnit) {
            MILLISECONDS -> 1
            SECONDS -> 1000
            MINUTES -> 60000
            else -> 1
        }
        this.timeout = (timeout * multiplier).toInt()
    }

    fun setTimeout(timeout: Long, timeUnit: java.util.concurrent.TimeUnit?) {
        require(timeout > 0) { "Timeout must be longer than 0" }
        this.timeout = java.util.concurrent.TimeUnit.MILLISECONDS
            .convert(timeout, timeUnit).toInt()
    }

    override fun startAnimation(animation: Animation) {
        if (isShown && !stopped) {
            super.startAnimation(animation)
        }
    }

    protected fun startAnimation() {
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
        }, timeout.toLong())
    }

    private fun stopAnimation() {
        handler.removeCallbacksAndMessages(null)
        animation?.cancel()
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(MILLISECONDS, SECONDS, MINUTES)
    annotation class TimeUnit

    companion object {
        private const val DEFAULT_TIME_OUT = 15000
        const val MILLISECONDS = 1
        const val SECONDS = 2
        const val MINUTES = 3
    }
}
