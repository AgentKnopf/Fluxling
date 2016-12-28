package com.agentknopf.fluxling.model

import android.graphics.Color
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by agentknopf on 28.12.16.
 */
fun getRandomColor(): Int = Color.argb(255, getInt(0, 256), getInt(0, 256), getInt(0, 256))

fun getInt(min: Int, max: Int): Int = ThreadLocalRandom.current().nextInt(min, max)
