package com.vng.live.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vng.live.R
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 21/06/2019
 */
class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateToSearchButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_searchFragment) }
        navigateToProfileDetailButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_profileDetailFragment) }
    }
}