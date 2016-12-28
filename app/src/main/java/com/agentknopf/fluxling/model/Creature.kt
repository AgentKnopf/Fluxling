package com.agentknopf.fluxling.model

import android.graphics.Point
import java.util.*

/**
 * Represents a creature. A creature has the following information: name, age in milliseconds, graphical makeup and dimensions. Creatures can be operated on, i.e.
 * actions can be carried out on them. Creatures are also capable of simple collision detection, i.e. colliding with each other and other objects such as the
 * edges of your screen.
 *
 * Created by agentknopf on 28.12.16.
 */
data class Creature(val name: String = createName(), val dimensions: Rectangle = createRectangle(10, 30)) {
    val id: String = UUID.randomUUID().toString()
}

fun createCreature(): Creature = Creature(dimensions = createRectangle(20, 50))

fun createName(): String {
    val alphabet: String = "abcdefghijklmnopqrstuvwxyz"
    val builder: StringBuilder = StringBuilder()
    //Minimum length is three, otherwise it doesn't really look like a name
    val length = getInt(3, 8);
    for (i in 1..length) builder.append(alphabet[getInt(0, alphabet.length)])
    builder.replace(0, 0, builder.get(0).toUpperCase().toString())
    return builder.toString();
}
