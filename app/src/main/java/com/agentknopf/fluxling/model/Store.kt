package com.agentknopf.fluxling.model

import com.agentknopf.fluxling.actions.AppStateAction

/**
 * Singleton store.
 * Created by agentknopf on 28.12.16.
 */
object Store {
    private var state = AppState()

    @Synchronized fun post(action: AppStateAction) {
        state = state.reduceState(action)
    }

    fun creatureCount(): Int = state.creatureCount()

    fun getIterator(): Iterator<Map.Entry<Creature, CreatureInfo>> = state.iterator()
}
