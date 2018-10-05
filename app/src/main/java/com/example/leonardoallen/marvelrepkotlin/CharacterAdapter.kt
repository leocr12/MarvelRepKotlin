package com.example.leonardoallen.marvelrepkotlin

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CharacterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isLoadingAdded = false

    private var characters: MutableList<Character>? = null
    private var context: Context? = null

    fun setListData(characters: MutableList<Character>) {
        this.characters = characters
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)

        viewHolder = when (viewType) {
            0 -> CharacterAdapterHolder(inflater.inflate(R.layout.character_list, parent, false))
            1 -> LoadingViewHolder(inflater.inflate(R.layout.item_progress, parent, false))
            else -> CharacterAdapterHolder(inflater.inflate(R.layout.character_list, parent, false))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characters!![position]
        when (getItemViewType(position)) {
            0 -> {
                val characterHolder = CharacterAdapterHolder(holder.itemView)
                characterHolder.name.text = character.name
                characterHolder.description.text = character.description
                Picasso.with(context).load(character.thumbnail.imageUrl).into(characterHolder.image)
                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ComicActivity::class.java)
                    intent.putExtra(EXTRA_MESSAGE, character)
                    context!!.startActivity(intent)
                }
            }

            1 -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return if (characters == null) 0 else characters!!.size
    }

    class CharacterAdapterHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var description: TextView
        internal var image: ImageView

        init {
            name = itemView.findViewById(R.id.character_name)
            description = itemView.findViewById(R.id.character_description)
            image = itemView.findViewById(R.id.character_image)
        }
    }

    class LoadingViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun add(character: Character) {
        characters!!.add(character)
        notifyItemChanged(characters!!.size - 1)
    }

    fun addAll(characterList: List<Character>) {
        for (character in characterList) {
            add(character)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Character(0, "", "", Thumbnail("")))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = characters!!.size - 1
        val item = getItem(position)

        if (item != null) {
            characters!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): Character? {
        return characters!![position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == characters!!.size - 1 && isLoadingAdded) 1 else 0
    }

    companion object {

        val EXTRA_MESSAGE = "_extra_"
    }
}