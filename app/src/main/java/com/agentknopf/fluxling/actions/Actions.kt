package com.agentknopf.fluxling.actions

import android.support.annotation.StringDef
import com.agentknopf.fluxling.model.AppState
import com.agentknopf.fluxling.model.Creature
import com.agentknopf.fluxling.model.CreatureInfo
import com.agentknopf.fluxling.model.createOffset
import java.util.*


/**
 * All actions related to the app state.
 */
interface AppStateAction {
    fun execute(state: AppState): AppState
}

/**
 * Actions that triggers movement of all creatures.
 */
class MoveAll : AppStateAction {
    override fun execute(state: AppState): AppState {
        val random: Random = Random()
        return AppState(state.creatureList.mapValues { it -> CreatureInfo(it.value.drawingOrigin.createWithOffset(createOffset(it.key.dimensions, random))) })
    }
}

data class RemoveCreature(val creature: Creature) : AppStateAction {
    override fun execute(state: AppState): AppState = AppState(state.creatureList.filterKeys { !it.equals(creature) })
}

/**
 * Actions that adds a new creature to the pool.
 */
data class AddCreature(val creature: Creature, val creatureInfo: CreatureInfo) : AppStateAction {
    override fun execute(state: AppState): AppState = AppState(state.creatureList.plus(Pair(creature, creatureInfo)))
}

/**
 * User interface related actions
 */
enum class GameActions {
    TOGGLE_PAUSE_RESUME
}

data class UiAction(val value: String, val action: UiActions)

enum class UiActions {
    //Expects the value string to be the message that'll be displayed
    SHOW_MESSAGE,
    //This expects the given value string to be parseable as an integer
    CHANGE_FAB_ICON
}
