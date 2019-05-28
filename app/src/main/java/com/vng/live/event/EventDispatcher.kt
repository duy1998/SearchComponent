package com.vng.live.event

import androidx.annotation.VisibleForTesting
import com.google.common.collect.HashMultimap
import com.google.common.collect.SetMultimap

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 23/05/2019
 */
class EventDispatcher {
    private val lock = Any()

    private val observers: SetMultimap<Int, EventListener> = HashMultimap.create()

    fun addListener(eventId: Int, listener: EventListener?) {
        listener?.run {
            synchronized(lock) {
                observers.put(eventId, listener)
            }
        }
    }

    fun removeListener(eventId: Int, listener: EventListener?) {
        listener?.run {
            synchronized(lock) {
                observers.remove(eventId, listener)
            }
        }
    }

    fun post(eventId: Int, vararg args: Any) {
        synchronized(lock) {
            observers.get(eventId)
        }?.forEach { it.onEvent(eventId, *args) }
    }

    @VisibleForTesting
    fun getListeners(eventId: Int): MutableSet<EventListener> = observers[eventId]

    @VisibleForTesting
    fun getListenerCount() = observers.values().size

    @VisibleForTesting
    fun clear() = observers.clear()

    companion object {
        val instance by lazy { EventDispatcher() }
    }

    interface EventListener {
        @VisibleForTesting
        val name: String

        fun onEvent(id: Int, vararg args: Any)
    }
}
