package com.vng.live.ui.main.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vng.live.R
import com.vng.live.data.model.SimpleProfile
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_item_search.view.*

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val items: MutableList<SimpleProfile> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_search,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as SearchViewHolder).bind(holder, items[position]) }
    fun setData(list: List<SimpleProfile>?) {
        items.clear()
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addMoreData(list: List<SimpleProfile>?){
        val currCount = items.size
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
            notifyItemRangeInserted(currCount, list.size)
        }
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: CircleImageView = itemView.avatar

        private val displayName :TextView=itemView.displayName

        private val profileID:TextView=itemView.profileId

        private val liveBox : LinearLayout =itemView.liveLinear

        private val followState:ImageView= itemView.followState

        @SuppressLint("SetTextI18n")
        fun bind (holder: SearchViewHolder, item:SimpleProfile){
            Glide.with(itemView)
                .load(item.avatar)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.img_empty_avatar_100)
                .override(itemView.resources.getDimensionPixelSize(R.dimen.search_avatar_size))
                .into(holder.avatar)
            holder.displayName.text=item.displayName
            holder.profileID.text= "ID: "+item.userId.toString()
            if (item.isLive)
                holder.liveBox.visibility= View.VISIBLE
            else
                holder.liveBox.visibility=View.INVISIBLE
            if (item.isFollow==1)
                holder.followState.setImageResource(R.mipmap.ic_chart_followed)
            else
                holder.followState.setImageResource(R.mipmap.ic_chart_follow)

        }

    }
}