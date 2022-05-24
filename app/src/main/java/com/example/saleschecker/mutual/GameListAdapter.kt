package com.example.saleschecker.mutual

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.saleschecker.R
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.databinding.GameItemBinding
import com.example.saleschecker.mutual.GlideObject.Companion.loadPicture
import com.example.saleschecker.utils.ResourceProvider
import com.example.saleschecker.utils.UrlBuilder
import javax.inject.Inject

class GameListAdapter @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val listener: OnItemClickListener
) : ListAdapter<GameEntity, GameListAdapter.WishListViewHolder>(gameDiffUtil) {

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
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null){
                        listener.onItemClick(item)
                    }
                }

            }
        }
        fun bind(item: GameEntity) {
            binding.gamePicture.loadPicture(UrlBuilder.getImageUrl(item.id))
            binding.gameName.text = item.name
            binding.gamePrice.text = when (item.price) {
                0f -> resourceProvider.getStringResource(R.string.free_game)
                Constants.DEFAULT_PRICE -> {
                    if (item.is_free_game) {
                        resourceProvider.getStringResource(R.string.free_game)
                    } else {
                        resourceProvider.getStringResource(R.string.price_is_unavailable)
                    }
                }
                else -> item.price.toString().plus(" ${item.currency}")
            }

            binding.root.setOnClickListener {

                binding.root.findNavController()
                    .navigate(R.id.action_homeFragment_to_detailsFragment)

            }
//            if (item.price != 0f) item.price.toString().plus(" ${ item.currency }") else "Free"
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: GameEntity)
    }

    companion object {
        private val gameDiffUtil = object : DiffUtil.ItemCallback<GameEntity>() {
            override fun areItemsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
                return areContentsTheSame(oldItem, newItem)
            }
            override fun areContentsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.price == newItem.price
                        && oldItem.discount_pct == newItem.discount_pct
            }
        }
    }
}