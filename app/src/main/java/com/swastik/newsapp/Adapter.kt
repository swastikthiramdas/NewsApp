package com.swastik.newsapp


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view.view.*

class Adapter(private var item : ArrayList<Data> = ArrayList()) : RecyclerView.Adapter<Adapter.viewholder>() {


    class viewholder(view: View) : RecyclerView.ViewHolder(view) {

        var tittle = view.tittle!!
        var image = view.image_view!!
        var discription = view.description_name!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.view,parent,false)
        return viewholder(itemview)

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        val currentpossition = item[position]
        holder.tittle.text = currentpossition.title
        holder.discription.text = currentpossition.description
        var link = currentpossition.link
        Glide.with(holder.itemView.context).load(currentpossition.image_url).into(holder.image)

        holder.itemView.setOnClickListener{
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(holder.itemView.context, Uri.parse(link))
        }

    }

    override fun getItemCount(): Int {
        return item.size

    }
}