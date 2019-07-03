package com.vng.live.ui.main.widget

import android.text.Editable
import android.text.TextWatcher

class TextWatcherSearchListener: TextWatcher {

    private var searchLoader:SearchLoader?=null

    var currKeyword:String=""

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        searchLoader!!.setDataAfterSearch(s.toString())
    }

    fun setSearchLoader(searchLoader: SearchLoader){
        this.searchLoader=searchLoader
    }

}
interface SearchLoader{
    fun setDataAfterSearch(s:String)
}