package com.example.saleschecker.wishlistfragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.saleschecker.databinding.FragmentWishListBinding
import com.example.saleschecker.mutual.FragmentWithRecycler
import com.example.saleschecker.mutual.GameListAdapter
import com.example.saleschecker.utils.observeWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WishListFragment : FragmentWithRecycler(), BackButtonCallbackReceiver {

    lateinit var binding: FragmentWishListBinding

    private val viewModel: WishListViewModel by viewModels()

    @Inject
    lateinit var gameListAdapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = binding.wishListRecycler

        initRecycler()
        observe()

        addBackPressCallback()

        savedInstanceState ?: restoreSavedFromBackButtonCallbackRecyclerState()
    }

    private fun addBackPressCallback() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            BackButtonCallback(this),
        )

    }

    private fun initRecycler() {
        recycler?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = gameListAdapter
        }
    }
    private fun observe() {
        viewModel.games.observeWithLifecycle(viewLifecycleOwner) {
            gameListAdapter.submitList(it)
            restoreRecyclerState()
        }
    }

    override fun onBackButtonPressed() {
        backButtonRecyclerSavedState = recycler?.layoutManager?.onSaveInstanceState()
        Log.e(TAG, "onBackButtonPressed: saving state : $backButtonRecyclerSavedState", )
        findNavController().popBackStack()
    }

    private fun restoreSavedFromBackButtonCallbackRecyclerState() {
        savedRecyclerState = backButtonRecyclerSavedState
        backButtonRecyclerSavedState = null
    }

    companion object {
        internal var backButtonRecyclerSavedState: Parcelable? = null
    }
}