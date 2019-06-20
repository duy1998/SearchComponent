package com.vng.live.event

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 23/05/2019
 */
class EventDispatcherTest {

    private var listener1: EventDispatcher.EventListener? = null

    private var listener2: EventDispatcher.EventListener? = null

    private var listener3: EventDispatcher.EventListener? = null

    private var listener4: EventDispatcher.EventListener? = null

    @Before
    fun setup() {
        listener1 = object : EventDispatcher.EventListener {
            override val name: String
                get() = "Listener1"

            override fun onEvent(id: Int, vararg args: Any) {
            }
        }

        listener2 = object : EventDispatcher.EventListener {
            override val name: String
                get() = "Listener2"

            override fun onEvent(id: Int, vararg args: Any) {
            }
        }

        listener3 = object : EventDispatcher.EventListener {
            override val name: String
                get() = "Listener3"

            override fun onEvent(id: Int, vararg args: Any) {
            }
        }

        listener4 = object : EventDispatcher.EventListener {
            override val name: String
                get() = "Listener4"

            override fun onEvent(id: Int, vararg args: Any) {
            }
        }
    }

    @Test
    fun testAddListener() {
        addListeners()
        EventDispatcher.instance.apply {
            assertEquals(2, getListeners(1).size)
            assertEquals(3, getListenerCount())
        }
    }

    private fun addListeners() {
        EventDispatcher.instance.apply {
            addListener(1, listener1)
            addListener(1, listener2)
            addListener(2, listener1)
            addListener(1, listener1)
        }
    }

    @Test
    fun testRemoveListener() {
        addListeners()
        EventDispatcher.instance.apply {
            removeListener(1, listener1)
            assertEquals(1, getListeners(1).size)
            assertEquals(listener2, getListeners(1).first())

            removeListener(1, listener1)
            removeListener(1, listener2)
            removeListener(2, listener1)
            assertEquals(0, getListenerCount())
        }
    }

    @After
    fun tearDown() = EventDispatcher.instance.clear()
}
