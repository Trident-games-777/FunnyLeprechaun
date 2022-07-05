package com.hp.printercontro.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hp.printercontro.R

class LeprechaunGame : AppCompatActivity() {
    private lateinit var item1: CardView
    private lateinit var item2: CardView
    private lateinit var item3: CardView

    private lateinit var iv1: ImageView
    private lateinit var iv2: ImageView
    private lateinit var iv3: ImageView

    private lateinit var tvCoins: TextView

    private lateinit var fab: FloatingActionButton
    private var coins = 0
    private val currentImages = mutableListOf<Int>()

    private val pictures = listOf(
        R.drawable.a,
        R.drawable.hat,
        R.drawable.j,
        R.drawable.k,
        R.drawable.luck
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leprechaun_game)

        item1 = findViewById(R.id.item1)
        item2 = findViewById(R.id.item2)
        item3 = findViewById(R.id.item3)

        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)

        tvCoins = findViewById(R.id.tvCoins)

        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            currentImages.clear()
            it.isEnabled = false
            it.visibility = View.INVISIBLE
            swing(item1, iv1)
            swing(item2, iv2)
            swing(item3, iv3) {
                if (currentImages[0] == currentImages[1] && currentImages[1] == currentImages[2]) {
                    tvCoins.text = (++coins).toString()
                }
                it.isEnabled = true
                it.visibility = View.VISIBLE
            }
        }
    }

    private fun swing(card: View, img: ImageView, onEnd: () -> Unit = {}) {
        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(card, "rotation", 0f, 360f).apply {
                    duration = ANIM_DURATION
                },
                ValueAnimator.ofFloat(1f, 0f).apply {
                    duration = ANIM_DURATION / 2
                    addUpdateListener {
                        img.alpha = it.animatedValue as Float
                    }
                    doOnEnd {
                        val randomPicture = pictures.random()
                        img.setImageResource(randomPicture)
                        currentImages.add(randomPicture)
                    }
                },
                ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = ANIM_DURATION / 2
                    addUpdateListener {
                        img.alpha = it.animatedValue as Float
                    }
                    startDelay = ANIM_DURATION / 2
                }
            )
            doOnEnd { onEnd() }
            start()
        }
    }

    companion object {
        private const val ANIM_DURATION = 500L
    }
}