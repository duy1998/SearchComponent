package com.vng.live.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vng.live.LiveApplication
import com.vng.live.R
import com.vng.live.data.model.SimpleProfile
import com.vng.live.di.presentation.PresentationComponent
import com.vng.live.ui.main.widget.*
import com.vng.live.util.isNetworkConnected
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

/**
 * Copyright (C) 2017, VNG Corporation.
 *
 * @author namnt4
 * @since 21/06/2019
 */
class SearchFragment : Fragment(), SearchView {

    private val searchAdapter: SearchAdapter = SearchAdapter()

    private val topStarAdapter:SearchAdapter= SearchAdapter()

    private val presenter: SearchPresenter = SearchPresenter()

    private val uiComponent: PresentationComponent = LiveApplication.instance.injector.userComponent.plusUiComponent()

    private lateinit var listenerSearchList: EndlessRecyclerViewListener

    private val listenerSearchEditText: TextWatcherSearchListener = TextWatcherSearchListener()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.attachView(this)

        uiComponent.inject(presenter)

        topStarList.layoutManager = LinearLayoutManager(context)

        topStarList.adapter= topStarAdapter

        searchList.layoutManager=LinearLayoutManager(context)

        searchList.adapter = searchAdapter

        listenerSearchList = EndlessRecyclerViewListener(presenter.paging, searchList.layoutManager as LinearLayoutManager)

        listenerSearchList.setPageLoader(object:PageLoader{
            override fun loadNextPage() {
                presenter.getSearchData(listenerSearchEditText.currKeyword,true)
            }
        })

        searchList.addOnScrollListener(listenerSearchList)

        listenerSearchEditText.setSearchLoader(object : SearchLoader {
            override fun setDataAfterSearch(s: String) {
                listenerSearchEditText.currKeyword=s
                if (s == "") {
                    resetListStar()
                    searchLayout.visibility =View.INVISIBLE
                    doneImage.visibility = View.INVISIBLE
                } else {
                    presenter.resetPagingInSearching(s)
                    searchLayout.visibility=View.VISIBLE
                    doneImage.visibility = View.VISIBLE
                }
            }

        })

        doneImage.setOnClickListener {
            searchEditText.setText("")
            setTopStarLayoutState(true)
            setSearchLayoutState(false)
            doneImage.visibility = View.INVISIBLE
        }

        exitTextView.setOnClickListener { findNavController().navigate(R.id.mainFragment) }

        swipeRefreshLayout.setOnRefreshListener { presenter.getListStarData() }

        if (isNetworkConnected()) {
            presenter.getListStarData()
        } else {
            noNetworkLayout.visibility = View.VISIBLE
        }
    }

    override fun setTopStartData(list: List<SimpleProfile>?) {
        topStarAdapter.setData(list)
    }

    override fun setInitSearchData(list: List<SimpleProfile>?) {
        searchAdapter.setData(list)
    }

    override fun addMoreSearchData(list: List<SimpleProfile>?) {
        searchAdapter.addMoreData(list)
    }

    override fun hideRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun setNoNetworkLayoutState(status: Boolean) {
        if (status) {
            noNetworkLayout.visibility = View.VISIBLE
            setInitSearchData(null)
            setTopStartData(null)
        } else
            noNetworkLayout.visibility = View.INVISIBLE
    }

    override fun setSwipeRefreshInTopStarLayoutState(status: Boolean) {
        swipeRefreshLayout.isEnabled = status
    }

    fun resetListStar() {
        presenter.getListStarData()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun setSearchLayoutState(status: Boolean) {
        if (status){
            searchLayout.visibility=View.VISIBLE
        }else{
            searchLayout.visibility=View.INVISIBLE
        }
    }

    override fun setTopStarLayoutState(status: Boolean) {
        if(status){
            topStarLayout.visibility=View.VISIBLE
        }else{
            topStarLayout.visibility=View.INVISIBLE
        }
    }

    override fun setEmptyTopStarLayoutState(status: Boolean) {
        if(status){
            emptyTopStarLayout.visibility=View.VISIBLE
            setTopStartData(null)
        }else{
            emptyTopStarLayout.visibility=View.INVISIBLE
        }
    }
}

interface SearchView {

    fun setNoNetworkLayoutState(status: Boolean)

    fun setEmptyTopStarLayoutState(status: Boolean)

    fun setSwipeRefreshInTopStarLayoutState(status: Boolean)

    fun setTopStarLayoutState(status: Boolean)

    fun setSearchLayoutState(status: Boolean)

    fun setTopStartData(list: List<SimpleProfile>?)

    fun setInitSearchData(list: List<SimpleProfile>?)

    fun addMoreSearchData(list: List<SimpleProfile>?)

    fun hideRefreshing()

}
