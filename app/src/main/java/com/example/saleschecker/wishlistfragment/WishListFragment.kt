package com.example.saleschecker.wishlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saleschecker.databinding.FragmentWishListBinding
import com.example.saleschecker.mutual.GameListAdapter
import com.example.saleschecker.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment : Fragment() {

    private lateinit var binding: FragmentWishListBinding

    private val viewModel: WishListViewModel by viewModels()

    private val gameListAdapter: GameListAdapter = GameListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observe()
    }


    private fun initRecycler() {
        binding.wishListRecycler.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = gameListAdapter
        }
    }

    private fun observe() {
        viewModel.games.observeWithLifecycle(viewLifecycleOwner) {
            gameListAdapter.submitList(it)
        }
    }

}