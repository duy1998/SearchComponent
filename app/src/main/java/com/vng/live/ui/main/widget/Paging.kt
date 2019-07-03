package com.vng.live.ui.main.widget

class Paging(pageSize: Int, var currentPage: Int) : AbstractPaging(pageSize) {
    fun getNext(): Int {
        return currentPage + 1
    }

    fun next() {
        currentPage += 1
    }

    fun isFirstPage(): Boolean {
        return currentPage == 1
    }
    override fun isBeforeFirstPage(): Boolean {
        return currentPage == 0
    }

    override fun reset() {
        super.reset()
        currentPage = 0
    }
}