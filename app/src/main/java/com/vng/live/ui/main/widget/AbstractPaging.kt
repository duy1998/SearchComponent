package com.vng.live.ui.main.widget

abstract class AbstractPaging  : EndlessScrollPaging {

    companion object {
        const val DEFAULT_PAGE_SIZE: Int = 30
    }

    var pageSize:Int = DEFAULT_PAGE_SIZE

    var hasNext:Boolean = true

    var isLoading:Boolean = false

    constructor(){
        this.pageSize= DEFAULT_PAGE_SIZE
    }

    constructor(pageSize:Int){
        this.pageSize=pageSize
    }

    override fun reset() {
        hasNext=true
    }
}

