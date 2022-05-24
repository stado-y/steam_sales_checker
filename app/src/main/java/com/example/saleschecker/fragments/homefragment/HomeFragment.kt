package com.example.saleschecker.fragments.homefragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.databinding.FragmentHomeBinding
import com.example.saleschecker.mutual.Constants
import com.example.saleschecker.mutual.FragmentWithRecycler
import com.example.saleschecker.mutual.GameListAdapter
import com.example.saleschecker.utils.gone
import com.example.saleschecker.utils.observeWithLifecycle
import com.example.saleschecker.utils.showSelf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : FragmentWithRecycler() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var gameListAdapter: GameListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        Log.e(TAG, "onCreateView: ", )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = binding.homeScreenRecycler

        observeGames()
        observeUserId()
        initRecycler()
    }

    private fun observeUserId() {
        viewModel.userId.observe(viewLifecycleOwner) {
            showLoginOrWishListButton(it == Constants.DEFAULT_USER_ID || it == null)
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
        recycler?.apply {
            layoutManager = LinearLayoutManager(this.context)
            if (adapter != gameListAdapter) {
                adapter = gameListAdapter
            }
        }
    }

    private fun observeGames() {
        viewModel.games.observeWithLifecycle(viewLifecycleOwner) {
            submitListForAdapter(it)
        }
    }

    private fun submitListForAdapter(list: List<GameEntity>) {
        val sorted: ArrayList<GameEntity> = arrayListOf()
        list.forEach { item ->
            if (!item.is_free_game) {
                sorted.add(item)
            }
        }
        sorted.sortByDescending { item -> item.discount_pct }
        gameListAdapter.submitList(sorted.toList())

        restoreRecyclerState()
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