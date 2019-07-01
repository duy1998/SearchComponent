package com.vng.live

import android.util.Log
import com.vng.live.data.Response
import com.vng.live.data.SimpleProfile
import com.vng.live.data.remote.rest.api.Services
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchPresenter {
    private var view:SearchView?=null

    private val service: Services = ApiUtils.getServices()

    fun attach(view: SearchView){
        this.view=view
    }

    fun detach(){
        this.view=null
    }

    fun getInitData(){
            service.listTopStar("11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object :Subscriber<Response<List<SimpleProfile>>>(){
                        override fun onNext(t: Response<List<SimpleProfile>>?) {
                                t?.apply {
                                    view!!.setData(this.data as ArrayList<SimpleProfile>)
                                }
                        }

                        override fun onCompleted() {
                            Log.d("qwe","Completed")
                             }

                        override fun onError(e: Throwable?) {
                            Log.d("qwe",e.toString())
                                    }

                    })


    }

    fun getData(s:String, page:Int){
        service.search(s,"11ed2864992e870c67e68e08f8039c6c5e8b20910cf1f939",1,10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Subscriber<Response<List<SimpleProfile>>>(){
                override fun onNext(t: Response<List<SimpleProfile>>?) {
                    t?.apply {
                        view!!.setData(t.data as ArrayList<SimpleProfile>)
                    }
                }

                override fun onCompleted() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(e: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
    }
}