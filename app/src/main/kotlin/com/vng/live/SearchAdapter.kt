package com.vng.live

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vng.live.data.SimpleProfile
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_item_search.view.*

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val items: ArrayList<SimpleProfile> = ArrayList()
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
        items[position]?.let {
            (holder as SearchViewHolder).bind(holder, it) }
    }

    fun setData(list: ArrayList<SimpleProfile>){
        items.clear()
        list?.apply {
            items.addAll(this)
            notifyDataSetChanged()

        }

    }

    fun addMoreData(list: ArrayList<SimpleProfile>){
        list?.apply {
            items.addAll(this)
            notifyDataSetChanged()
        }
    }
    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: CircleImageView = itemView.avatar

        private val displayName :TextView=itemView.displayName

        private val profileID:TextView=itemView.profileId

        private val liveBox:LiveBox= itemView.liveBox

        private val followState:ImageView= itemView.followState

        fun bind (holder: SearchViewHolder, item:SimpleProfile){
            Glide.with(itemView)
                    .load(item.avatar)
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