package com.agentknopf.fluxling.model

import android.graphics.Color
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Represents a rectangle independent of the space it may be drawn onto.
 *
 * Created by agentknopf on 28.12.16.
 */
data class Rectangle(val width: Int = 16, val height: Int = 10, val color: Int = Color.WHITE)


/**
 * Creates a rectangle with random dimensions which are however within the given bounds and a random color.
 */
fun createRectangle(min: Int, max: Int): Rectangle {
    val width = ThreadLocalRandom.current().nextInt(min, max);
    val height = ThreadLocalRandom.current().nextInt(min, max);
    return Rectangle(width, height, getRandomColor())
}

/**
 * Creates a a new point that serves as an offset based on the given Rectangle's width and height and a random factor.
 */
fun createOffset(dimensions: Rectangle, random: Random): Point = Point((dimensions.width / 2) * if (random.nextBoolean()) 1 else -1, (dimensions.height / 2) * if (random.nextBoolean()) 1 else -1)

/**
 * Represents a 2D points with it's x and y axis values
 */
data class Point(val x: Int, val y: Int) {
    fun createWithOffset(offset: Point): Point = Point(x + offset.x, y + offset.y)
}