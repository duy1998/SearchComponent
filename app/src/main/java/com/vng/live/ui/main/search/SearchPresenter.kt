package com.vng.live.ui.main.search

import com.vng.live.data.model.Response
import com.vng.live.data.model.SimpleProfile
import com.vng.live.data.remote.services.UserService
import com.vng.live.ui.BasePresenter
import com.vng.live.ui.main.widget.Paging
import com.vng.live.util.isNetworkConnected
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter : BasePresenter<SearchView>() {

    @Inject
    lateinit var service: UserService

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var paging: Paging

    override fun attachView(view: SearchView) {
        super.attachView(view)
        paging = Paging(10, 0)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    fun getListStarData() {
        view?.let {
            it.setTopStarLayoutState(true)
            if (isNetworkConnected()) {
                service.listTopStar("11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(TopStarObserver(this))
            } else {
                it.setNoNetworkLayoutState(true)
            }
            it.hideRefreshing()
        }


    }

    fun getSearchData(s: String, loadingState:Boolean) {
        view?.let {
            it.setSearchLayoutState(true)
            if (isNetworkConnected()) {
                if (loadingState){
                    paging.isLoading=true
                }
                service.search(s, "11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939", paging.getNext(), paging.pageSize)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(SearchObserver(this))
            } else {
                it.setNoNetworkLayoutState(true)
            }
            it.hideRefreshing()
        }
    }

    fun resetPagingInSearching(s: String) {
        paging.reset()
        getSearchData(s,false)
    }

    class TopStarObserver(private val presenter: SearchPresenter) : Observer<Response<List<SimpleProfile>>> {
        private lateinit var disposable: Disposable

        override fun onSubscribe(d: Disposable) {
            disposable = d
            presenter.compositeDisposable.add(d)
        }

        override fun onComplete() {
            disposable.dispose()
        }

        override fun onNext(t: Response<List<SimpleProfile>>) {
            if(isNetworkConnected()){
                if(t.data!!.isNullOrEmpty()) {
                    presenter.view!!.setTopStartData(t.data)
                }else{
                    presenter.view!!.setEmptyTopStarLayoutState(true)
                }
            }else{
                presenter.view?.setNoNetworkLayoutState(true)
            }

        }

        override fun onError(e: Throwable) {
            if (e.isNetworkConnected()) {
                presenter.view?.setNoNetworkLayoutState(true)
            }
        }
    }
    class SearchObserver(private val presenter: SearchPresenter) : Observer<Response<List<SimpleProfile>>> {
        private lateinit var disposable: Disposable

        override fun onSubscribe(d: Disposable) {
            disposable = d
            presenter.compositeDisposable.add(d)
        }

        override fun onComplete() {
            presenter.paging.isLoading=false
            presenter.view?.setLoadmoreState(presenter.paging.isLoading)
            disposable.dispose()
        }

        override fun onNext(t: Response<List<SimpleProfile>>) {
            presenter.view?.setTopStartData(t.data)
        }

        override fun onError(e: Throwable) {
            if (e.isNetworkConnected()) {
                presenter.view?.setNoNetworkLayoutState(true)
            }
        }
    }
}