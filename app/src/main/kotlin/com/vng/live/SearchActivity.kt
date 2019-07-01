package com.vng.live

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vng.live.data.SimpleProfile
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchActivity : AppCompatActivity(),SearchView {
    private val mAdapter:SearchAdapter= SearchAdapter()

    private val presenter: SearchPresenter= SearchPresenter()

    private var page:Int=0

    private var isLoading:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter.attach(this)

        deleteText.setOnClickListener {
            edtSearch.setText("")
            deleteText.visibility=View.INVISIBLE
        }

        searchList.addOnScrollListener(object :RecyclerView.OnScrollListener(){

        })

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString() == "") {
                    fetchInitData()
                    deleteText.visibility = View.INVISIBLE

                } else {
                    page=1
                    swipeRefreshLayout.isEnabled=false
                    presenter.getData(s.toString(),page)
                    deleteText.visibility = View.VISIBLE
                }

            }

        })

        searchList.layoutManager=LinearLayoutManager(this)

        searchList.adapter=mAdapter

        presenter.getInitData()
    }

    fun fetchInitData(){
        swipeRefreshLayout.isEnabled=true
        presenter.getInitData()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun setData(list: ArrayList<SimpleProfile>) {
        mAdapter.setData(list)
    }

    override fun addMoreData(list: ArrayList<SimpleProfile>) {
        mAdapter.addMoreData(list)
    }

    override fun setPage() {
        page++
    }

}
interface SearchView{
    fun setData(list: ArrayList<SimpleProfile>)

    fun addMoreData(list: ArrayList<SimpleProfile>)

    fun setPage()

}