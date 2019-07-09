package com.vng.live.ui.main.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vng.live.LiveApplication
import com.vng.live.R
import com.vng.live.data.model.SimpleProfile
import com.vng.live.di.presentation.PresentationComponent
import com.vng.live.ui.main.widget.EndlessRecyclerViewListener
import com.vng.live.ui.main.widget.PageLoader
import com.vng.live.ui.main.widget.SearchLoader
import com.vng.live.ui.main.widget.TextWatcherSearchListener
import com.vng.live.util.isNetworkConnected
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_no_network.view.*
import kotlinx.android.synthetic.main.toolbar_search.*

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 21/06/2019
 */
class SearchFragment : Fragment() {

    private val searchAdapter: SearchAdapter = SearchAdapter()

    private val topStarAdapter: SearchAdapter = SearchAdapter()

    private val uiComponent: PresentationComponent = LiveApplication.instance.injector.userComponent.plusUiComponent()

    private lateinit var viewModel: SearchViewModel

    private lateinit var listenerSearchList: EndlessRecyclerViewListener

    private val listenerSearchEditText: TextWatcherSearchListener = TextWatcherSearchListener()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        uiComponent.inject(viewModel.searchRepository)

        viewModel.init()

        topStarList.layoutManager = LinearLayoutManager(context)

        topStarList.adapter = topStarAdapter

        searchList.layoutManager = LinearLayoutManager(context)

        searchList.adapter = searchAdapter

        viewModel.topStarList.observe(this,
            Observer<List<SimpleProfile>> { t -> setTopStartData(t) })

        viewModel.searchList.observe(this,
            Observer<List<SimpleProfile>> { t ->
                if (searchAdapter.itemCount == 0) {
                    setInitSearchData(t)
                } else {
                    Log.d("TestAdd", "ADD..............")
                    addMoreSearchData(t)
                }
            })

        //*********Top Star Layout*********************************************//
        viewModel.flagEmptyLayoutTopStar.observe(this, Observer<Boolean> { t -> setEmptyTopStarLayoutState(t) })

        viewModel.flagLoadingTopStar.observe(this, Observer<Boolean> { t -> setLoadingTopStarLayoutState(t) })

        viewModel.flagNoNetworkTopStar.observe(this, Observer<Boolean> { t -> setNoNetworkTopStarLayoutState(t) })

        viewModel.flagListTopStar.observe(this, Observer<Boolean> { t -> setTopStarListState(t) })

        viewModel.flagLayoutTopStar.observe(this, Observer<Boolean> { t -> setTopStarLayoutState(t) })

        viewModel.flagSwipeRefreshTopStar.observe(this, Observer<Boolean> { t -> setSwipeRefreshState(t) })
        //*********************************************************************//

        //*********Search Layout*********************************************//
        viewModel.flagNoNetworkSearch.observe(this, Observer<Boolean> { t -> setNoNetworkSearchLayoutState(t) })

        viewModel.flagLoadingSearch.observe(this, Observer<Boolean> { t -> setLoadingSearchLayoutState(t) })

        viewModel.flagListSearch.observe(this, Observer<Boolean> { t -> setSearchListState(t) })

        viewModel.flagLayoutSearch.observe(this, Observer<Boolean> { t -> setSearchLayoutState(t) })
        //******************************************************************//

        listenerSearchList = EndlessRecyclerViewListener(
            viewModel.paging,
            searchList.layoutManager as LinearLayoutManager
        )

        noNetworkTopStarLayout.retryButton.setOnClickListener { v ->
            Log.d("Test ID parent", (v!!.parent as View).id.toString())
            resetListTopStar()
        }

        noNetworkSearchLayout.retryButton.setOnClickListener {
            if (isNetworkConnected()) {
                viewModel.resetPaging()
                searchList(searchEditText.text.toString())
                viewModel.flagListSearch.value = true
            }
        }

        listenerSearchList.setPageLoader(object : PageLoader {
            override fun loadNextPage() {
                viewModel.getSearchList(
                    this@SearchFragment,
                    "11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939",
                    listenerSearchEditText.currKeyword
                )
            }
        })

        searchList.addOnScrollListener(listenerSearchList)

        listenerSearchEditText.setSearchLoader(object : SearchLoader {
            override fun setDataAfterSearch(s: String) {
                setInitSearchData(null)
                listenerSearchEditText.currKeyword = s
                if (s == "") {
                    viewModel.flagLayoutTopStar.value = true
                    resetListTopStar()
                    doneImage.visibility = View.INVISIBLE
                } else {
                    viewModel.flagLayoutSearch.value = true
                    searchList(s)
                    doneImage.visibility = View.VISIBLE
                }
            }

        })

        searchEditText.addTextChangedListener(listenerSearchEditText)

        doneImage.setOnClickListener {
            searchEditText.setText("")
            setTopStarLayoutState(true)
            doneImage.visibility = View.INVISIBLE
        }

        exitTextView.setOnClickListener { findNavController().navigate(R.id.mainFragment) }

        topStarSwipeRefreshLayout.setOnRefreshListener { resetListTopStar() }

        viewModel.flagLayoutTopStar.value = true
        if (isNetworkConnected()) {
            resetListTopStar()
        } else {
            viewModel.flagNoNetworkTopStar.value = true
        }
    }

    fun setTopStartData(list: List<SimpleProfile>?) {
        topStarAdapter.setData(list)
    }

    fun setInitSearchData(list: List<SimpleProfile>?) {
        searchAdapter.setData(list)
    }

    fun addMoreSearchData(list: List<SimpleProfile>?) {
        searchAdapter.addMoreData(list)
    }

    fun setSwipeRefreshState(status: Boolean) {
        topStarSwipeRefreshLayout.isRefreshing = status
    }

    fun resetListTopStar() {
        viewModel.getTopStarList(this, "11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939")
    }

    fun searchList(s: String) {
        viewModel.resetPaging()
        viewModel.getSearchList(this, "11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939", s)
    }

    //*************Top Star Layout**************************//
    fun setTopStarLayoutState(status: Boolean) {
        if (status) {
            topStarLayout.visibility = View.VISIBLE
            setSearchLayoutState(false)
        } else {
            topStarLayout.visibility = View.INVISIBLE
        }
    }

    fun setEmptyTopStarLayoutState(status: Boolean) {
        if (status) {
            emptyTopStarLayout.visibility = View.VISIBLE
            setNoNetworkTopStarLayoutState(false)
            setLoadingTopStarLayoutState(false)
            setTopStarListState(false)
        } else {
            emptyTopStarLayout.visibility = View.INVISIBLE
        }
    }

    fun setLoadingTopStarLayoutState(status: Boolean) {
        if (status) {
            loadingTopStar.visibility = View.VISIBLE
            setNoNetworkTopStarLayoutState(false)
            setEmptyTopStarLayoutState(false)
            setTopStarListState(false)
        } else {
            loadingTopStar.visibility = View.INVISIBLE
        }
    }

    fun setNoNetworkTopStarLayoutState(status: Boolean) {
        if (status) {
            noNetworkTopStarLayout.visibility = View.VISIBLE
            setEmptyTopStarLayoutState(false)
            setLoadingTopStarLayoutState(false)
            setTopStarListState(false)
        } else {
            noNetworkTopStarLayout.visibility = View.INVISIBLE
        }
    }

    fun setTopStarListState(status: Boolean) {
        if (status) {
            topStarSwipeRefreshLayout.visibility = View.VISIBLE
            setNoNetworkTopStarLayoutState(false)
            setLoadingTopStarLayoutState(false)
            setEmptyTopStarLayoutState(false)
        } else {
            topStarSwipeRefreshLayout.visibility = View.INVISIBLE
            setTopStartData(null)
        }
    }
    //******************************************************//

    //**************Search Layout****************************//
    fun setSearchLayoutState(status: Boolean) {
        if (status) {
            searchLayout.visibility = View.VISIBLE
            setTopStarLayoutState(false)
        } else {
            searchLayout.visibility = View.INVISIBLE
        }
    }

    fun setLoadingSearchLayoutState(status: Boolean) {
        if (status) {
            loadingSearch.visibility = View.VISIBLE
            setNoNetworkSearchLayoutState(false)
            setSearchListState(false)
        } else {
            loadingSearch.visibility = View.INVISIBLE
        }
    }

    fun setNoNetworkSearchLayoutState(status: Boolean) {
        if (status) {
            noNetworkSearchLayout.visibility = View.VISIBLE
            setSearchListState(false)
        } else {
            noNetworkSearchLayout.visibility = View.INVISIBLE
        }
    }

    fun setSearchListState(status: Boolean) {
        if (status) {
            searchList.visibility = View.VISIBLE
        } else {
            searchList.visibility = View.INVISIBLE
        }
    }
    //********************************************************//
}
