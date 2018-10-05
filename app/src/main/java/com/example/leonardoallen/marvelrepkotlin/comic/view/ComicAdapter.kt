package com.example.leonardoallen.marvelrepkotlin.comic.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.leonardoallen.marvelrepkotlin.MarvelApplication
import com.example.leonardoallen.marvelrepkotlin.R
import com.example.leonardoallen.marvelrepkotlin.comic.util.ComicThumbnail
import com.example.leonardoallen.marvelrepkotlin.comic.model.Comic
import com.squareup.picasso.Picasso

class ComicAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private var comics: MutableList<Comic>? = null

    fun setComicData(comics: MutableList<Comic>) {
        this.comics = comics
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            0 -> viewHolder = ComicAdapterHolder(inflater.inflate(R.layout.comic_list, parent, false))
            1 -> viewHolder = LoadingViewHolder(inflater.inflate(R.layout.item_progress, parent, false))
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comic = comics!![position]

        when (getItemViewType(position)) {
            0 -> {
                val comicHolder = holder as ComicAdapterHolder
                comicHolder.title.text = comic.title
                comicHolder.description.text = comic.description
                Picasso.with(MarvelApplication.context.get()).load(comic.thumbnail.imageUrl).into(comicHolder.image)
                comicHolder.printPrice.text = calculatePrices(comic)
                comicHolder.digitalPurchasePrice.text = calculatePrices(comic)
            }
            1 -> {
            }
        }
    }

    private fun calculatePrices(comic: Comic): String {
        return if (comic.prices.size == 1) {
            java.lang.Double.toString(comic.prices[0].price)
        } else if (comic.prices.size == 2) {
            java.lang.Double.toString(comic.prices[1].price)
        } else {
            MarvelApplication.context.get()!!.getString(R.string.price_error_message)
        }
    }

    override fun getItemCount(): Int {
        return if (comics == null) 0 else comics!!.size
    }

    fun add(comic: Comic) {
        comics!!.add(comic)
        notifyItemChanged(comics!!.size - 1)
    }

    fun addAll(comicList: List<Comic>) {
        for (comic in comicList) {
            add(comic)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Comic(0, "", "", emptyList(), ComicThumbnail("")))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = comics!!.size - 1
        val item = getItem(position)

        if (item != null) {
            comics!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): Comic? {
        return comics!![position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == comics!!.size - 1 && isLoadingAdded) 1 else 0
    }

    inner class ComicAdapterHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView
        internal var description: TextView
        internal var image: ImageView
        internal var printPrice: TextView
        internal var digitalPurchasePrice: TextView

        init {
            title = itemView.findViewById(R.id.comic_title)
            description = itemView.findViewById(R.id.comic_description)
            image = itemView.findViewById(R.id.comic_image)
            printPrice = itemView.findViewById(R.id.print_price)
            digitalPurchasePrice = itemView.findViewById(R.id.digital_purchase_price)
        }
    }

    class LoadingViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}
