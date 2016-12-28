package com.agentknopf.fluxling.actions

import org.greenrobot.eventbus.EventBus

/**
 * Created by agentknopf on 28.12.16.
 */
object EventBus {
    val eventBus: EventBus = org.greenrobot.eventbus.EventBus.getDefault()

    fun register(subscriber: Any) {
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber)
        }
    }

    fun unRegister(subscriber: Any) {
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber)
        }
    }

    fun post(value: Any) = eventBus.post(value)
}