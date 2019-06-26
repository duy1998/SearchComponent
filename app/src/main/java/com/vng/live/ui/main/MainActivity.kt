package com.vng.live.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vng.live.R

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 21/06/2019
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    companion object {
        fun intentFor(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}