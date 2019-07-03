package com.vng.live.ui

interface Presenter<T> {
    fun attachView(view: T)
    fun detachView()
    fun isViewAttached(): Boolean
}