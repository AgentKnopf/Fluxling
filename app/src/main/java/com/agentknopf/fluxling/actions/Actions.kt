package com.agentknopf.fluxling.actions

import com.agentknopf.fluxling.model.AppState
import com.agentknopf.fluxling.model.Creature
import com.agentknopf.fluxling.model.CreatureInfo
import com.agentknopf.fluxling.model.createOffset
import java.util.*

/**
 * All Store related actions are gathered here.
 * Created by agentknopf on 28.12.16.
 */
interface AppStateAction {
    fun execute(state: AppState): AppState
}

class MoveAll : AppStateAction {
    override fun execute(state: AppState): AppState {
        val random: Random = Random()
        return AppState(state.creatureList.mapValues { it -> CreatureInfo(it.value.drawingOrigin.createWithOffset(createOffset(it.key.dimensions, random))) })
    }
}

data class AddCreature(val creature: Creature, val creatureInfo: CreatureInfo) : AppStateAction {
    override fun execute(state: AppState): AppState = AppState(state.creatureList.plus(Pair(creature, creatureInfo)))
}