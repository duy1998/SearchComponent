package com.vng.live.ui.main.search

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vng.live.data.model.SimpleProfile
import com.vng.live.ui.main.widget.Paging
import com.vng.live.util.isNetworkConnected

class SearchViewModel : ViewModel() {

    var searchRepository: SearchRepository = SearchRepository()

    lateinit var searchList: MutableLiveData<List<SimpleProfile>>

    lateinit var topStarList: MutableLiveData<List<SimpleProfile>>

    var paging: Paging = Paging(10, 0)

    var flagEmptyLayoutTopStar: MutableLiveData<Boolean> = MutableLiveData()

    var flagLoadingTopStar: MutableLiveData<Boolean> = MutableLiveData()

    var flagNoNetworkTopStar: MutableLiveData<Boolean> = MutableLiveData()

    var flagListTopStar: MutableLiveData<Boolean> = MutableLiveData()

    var flagLayoutTopStar: MutableLiveData<Boolean> = MutableLiveData()

    var flagSwipeRefreshTopStar: MutableLiveData<Boolean> = MutableLiveData()

    var flagLoadingSearch: MutableLiveData<Boolean> = MutableLiveData()

    var flagNoNetworkSearch: MutableLiveData<Boolean> = MutableLiveData()

    var flagListSearch: MutableLiveData<Boolean> = MutableLiveData()

    var flagLayoutSearch: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {
        flagLoadingTopStar = searchRepository.getFlagLoadingTopStar()
        flagNoNetworkTopStar = searchRepository.getFlagNoNetworkTopStar()
        flagListTopStar = searchRepository.getFlagListTopStar()
        flagSwipeRefreshTopStar = searchRepository.getFlagSwipeRefreshTopStar()
        flagEmptyLayoutTopStar = searchRepository.getFlagEmptyLayoutTopStar()
        flagLayoutTopStar = searchRepository.getFlagLayoutTopStar()

        flagLoadingSearch = searchRepository.getFlagLoadingSearch()
        flagNoNetworkSearch.value = false
        flagListSearch = searchRepository.getFlagListSearch()

        topStarList = searchRepository.getTopStarListLiveData()
        searchList = searchRepository.getSearchListLiveData()

    }

    fun getTopStarList(owner: LifecycleOwner, authKey: String) {
        if (isNetworkConnected()) {
            flagLoadingTopStar.value = true
            searchRepository.getTopStarList(authKey, owner)
        } else {
            flagNoNetworkTopStar.value = true
        }
    }

    fun getSearchList(owner: LifecycleOwner, authKey: String, s: String) {
        if (isNetworkConnected()) {
            if (paging.isBeforeFirstPage()) {
                flagLoadingSearch.value = true
                paging.isLoading = true
            }
            searchRepository.getSearchList(owner, authKey, s, paging)
        } else {
            flagNoNetworkSearch.value = true
        }

    }

    fun resetPaging() {
        paging.reset()
    }


}