package com.vng.live.ui.main.search

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.leonardoxh.livedatacalladapter.Resource
import com.vng.live.data.model.Response
import com.vng.live.data.model.SimpleProfile
import com.vng.live.data.remote.services.UserService
import com.vng.live.ui.main.widget.Paging
import com.vng.live.util.isNetworkDisConnected
import javax.inject.Inject

class SearchRepository {

    @Inject
    lateinit var service: UserService

    private val topStarList = MutableLiveData<List<SimpleProfile>>()

    private val searchList = MutableLiveData<List<SimpleProfile>>()


    private val flagListTopStar = MutableLiveData<Boolean>()

    private val flagLoadingTopStar = MutableLiveData<Boolean>()

    private val flagNoNetworkTopStar = MutableLiveData<Boolean>()

    private val flagEmptyLayoutTopStar = MutableLiveData<Boolean>()

    private val flagSwipeRefreshTopStar = MutableLiveData<Boolean>()

    private val flagLayoutTopStar=MutableLiveData<Boolean>()

    private val flagEmptyLayoutSearch = MutableLiveData<Boolean>()

    private val flagLoadingSearch = MutableLiveData<Boolean>()

    private val flagListSearch = MutableLiveData<Boolean>()

    fun getTopStarList(authKey: String, owner: LifecycleOwner) {
        service.listTopStar(authKey).observe(owner,
            Observer<Resource<Response<List<SimpleProfile>>>> { t ->
                flagSwipeRefreshTopStar.value=false
                if (t.isSuccess) {
                    if (!t.resource!!.data.isNullOrEmpty()) {
                        topStarList.value = t.resource!!.data
                        flagListTopStar.value = true
                        flagLoadingTopStar.value = false
                    } else {
                        flagEmptyLayoutTopStar.value = true
                    }
                } else {
                    if (t.error.isNetworkDisConnected()) {
                        flagNoNetworkTopStar.value = true
                    }
                }
            })
    }

    fun getSearchList(owner: LifecycleOwner, authKey: String, s: String, paging: Paging) {
        service.search(authKey, s, paging.getNext(),paging.pageSize)
            .observe(owner,
                Observer<Resource<Response<List<SimpleProfile>>>> { t->
                    if (t.isSuccess) {
                        flagLoadingSearch.value=false
                        t.resource?.data.let {
                            if (!it.isNullOrEmpty()) {
                                searchList.value=it
                                flagListSearch.value=true
                                paging.next()
                                paging.hasNext= it.size == paging.pageSize
                            }else{
                                paging.hasNext=false
                                if (paging.isBeforeFirstPage()){
                                    flagEmptyLayoutSearch.value=true
                                }
                            }
                        }
                        paging.isLoading=false
                    } else {
                        if (t.error.isNetworkDisConnected()) {
                        }
                    }
                })
    }


    fun getTopStarListLiveData(): MutableLiveData<List<SimpleProfile>> = topStarList

    fun getSearchListLiveData(): MutableLiveData<List<SimpleProfile>> = searchList


    fun getFlagListTopStar(): MutableLiveData<Boolean> = flagListTopStar

    fun getFlagLoadingTopStar(): MutableLiveData<Boolean> = flagLoadingTopStar

    fun getFlagNoNetworkTopStar(): MutableLiveData<Boolean> = flagNoNetworkTopStar

    fun getFlagEmptyLayoutTopStar(): MutableLiveData<Boolean> = flagEmptyLayoutTopStar

    fun getFlagSwipeRefreshTopStar(): MutableLiveData<Boolean> = flagSwipeRefreshTopStar

    fun getFlagLayoutTopStar() : MutableLiveData<Boolean> = flagLayoutTopStar

    fun getFlagLoadingSearch() : MutableLiveData<Boolean> = flagLoadingSearch

    fun getFlagListSearch() : MutableLiveData<Boolean> = flagListSearch
}
