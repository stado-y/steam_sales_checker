package com.example.saleschecker.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.databinding.FragmentHomeBinding
import com.example.saleschecker.mutual.GameListAdapter
import com.example.saleschecker.utils.gone
import com.example.saleschecker.utils.observeWithLifecycle
import com.example.saleschecker.utils.showSelf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val gameListAdapter: GameListAdapter = GameListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observeGames()
        observeUserId()
    }

    private fun observeUserId() {
        viewModel.userId.observe(viewLifecycleOwner) {
            showLoginOrWishListButton(it == null)
        }
    }

    private fun showLoginOrWishListButton(showLogin: Boolean) {
        var stubToShow = binding.stubGoToWishlist
        var stubToHide = binding.stubLoginPic
        if (showLogin) {
            stubToShow = binding.stubLoginPic
            stubToHide = binding.stubGoToWishlist
        }
        showStub(stubToShow)
        stubToHide.gone()
    }

    private fun initRecycler() {
        binding.homeScreenRecycler.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = gameListAdapter
        }
    }

    private fun observeGames() {
        viewModel.games.observeWithLifecycle(viewLifecycleOwner) {
            val sorted: ArrayList<GameEntity> = arrayListOf()
            it.forEach { item ->
                if (!item.is_free_game) {
                    sorted.add(item)
                }
            }
            sorted.sortByDescending { item -> item.discount_pct }
            gameListAdapter.submitList(sorted.toList())
        }
    }

    private fun showStub(stub: ViewStub) {
        when (stub) {
            binding.stubLoginPic -> stub.showSelf {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSteamAuthFragment())
            }
            else -> stub.showSelf {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWishListFragment())
            }
        }
    }
}