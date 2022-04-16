package com.example.saleschecker.mutual

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.saleschecker.data.local.GameEntity
import com.example.saleschecker.databinding.GameItemBinding
import com.example.saleschecker.mutual.GlideObject.Companion.loadPicture
import com.example.saleschecker.utils.UrlBuilder

class GameListAdapter: ListAdapter<GameEntity, GameListAdapter.WishListViewHolder>(gameDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val view = GameItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return WishListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }


    inner class WishListViewHolder(
        private val binding: GameItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameEntity) {

            binding.gamePicture.loadPicture(UrlBuilder.getImageUrl(item.id))
            binding.gameName.text = item.name
            binding.gamePrice.text = if (item.price != 0f) item.price.toString().plus(" ${ item.currency }") else "Free"
        }
    }


    companion object {
        private val gameDiffUtil = object: DiffUtil.ItemCallback<GameEntity>() {
            override fun areItemsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.price == newItem.price
                        && oldItem.discount_pct == newItem.discount_pct
            }
        }
    }
}