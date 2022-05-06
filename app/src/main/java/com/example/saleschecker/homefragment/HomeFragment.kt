package com.example.saleschecker.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saleschecker.data.local.games.GameEntity
import com.example.saleschecker.databinding.FragmentHomeBinding
import com.example.saleschecker.mutual.GameListAdapter
import com.example.saleschecker.utils.observeWithLifecycle
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

        inflateStub()

        initRecycler()
        observe()
    }

    private fun initRecycler() {
        binding.homeScreenRecycler.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = gameListAdapter
        }
    }

    private fun observe() {
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

    private fun inflateWishListStub() {
        val text = binding.stubGoToWishlist.inflate()

        text.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWishListFragment())
        }
    }

    private fun inflateLoginStub() {
        val loginPicture = binding.stubLoginPic.inflate()

        loginPicture.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSteamAuthFragment())
        }
    }

    private fun inflateStub() {
        viewModel.userId.value?.let {
            inflateWishListStub()
        } ?: inflateLoginStub()
    }
}