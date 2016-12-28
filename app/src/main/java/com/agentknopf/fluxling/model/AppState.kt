package com.agentknopf.fluxling.model

import com.agentknopf.fluxling.actions.AppStateAction

/**
 * Represents the game state. The list of creatures is initially empty.
 *
 * Created by agentknopf on 28.12.16.
 */
data class AppState(val creatureList: Map<Creature, CreatureInfo> = emptyMap()) : Iterable<Map.Entry<Creature, CreatureInfo>> {
    override fun iterator(): Iterator<Map.Entry<Creature, CreatureInfo>> = creatureList.iterator()

    fun reduceState(action: AppStateAction): AppState = action.execute(this)

    fun creatureCount() = creatureList.size
}

data class CreatureInfo(val drawingOrigin: Point)
