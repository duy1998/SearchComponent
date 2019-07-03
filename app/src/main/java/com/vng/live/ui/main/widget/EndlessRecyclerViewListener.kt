package com.vng.live.ui.main.widget

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class EndlessRecyclerViewListener(
    private val paging: Paging,
    private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    private var pageLoader: PageLoader? = null

    private var lastStaggerPosition: IntArray? = null

    private var visibleThreshold = 5

    init {
        if (layoutManager is GridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        loadNextPageIfNeeded(dy)
    }

    private fun loadNextPageIfNeeded(dy: Int) {
        if (!paging.hasNext || paging.isLoading) {
            return
        }

        if (isScrollNearBottom(dy) && pageLoader != null) {
            pageLoader!!.loadNextPage()
        }
    }

    private fun isScrollNearBottom(dy: Int): Boolean {
        val isScrollDown = dy > 0

        val lastVisibleIndex: Int

        val totalItem = layoutManager.itemCount

        when (layoutManager) {
            is LinearLayoutManager -> lastVisibleIndex = layoutManager.findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> {
                if (lastStaggerPosition == null) {
                    lastStaggerPosition = IntArray(layoutManager.spanCount)
                }

                layoutManager.findLastVisibleItemPositions(lastStaggerPosition)

                lastVisibleIndex = findMax(lastStaggerPosition!!)
            }
            else -> throw RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
        }

        return totalItem - lastVisibleIndex <= visibleThreshold && isScrollDown
    }

    private fun findMax(lastStaggerPosition: IntArray): Int {
        var max = Integer.MIN_VALUE

        for (value in lastStaggerPosition) {
            if (value > max) {
                max = value
            }
        }

        return max
    }

    fun setPageLoader(pageLoader: PageLoader) {
        this.pageLoader = pageLoader
    }

}

interface PageLoader {
    fun loadNextPage()
}