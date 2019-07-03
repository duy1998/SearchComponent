package com.vng.live.ui

open class BasePresenter<T> : Presenter<T> {
    protected var view: T? = null

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun isViewAttached(): Boolean {
        return null != view
    }
}