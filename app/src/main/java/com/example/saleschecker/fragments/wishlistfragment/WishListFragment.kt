package com.example.saleschecker.fragments.wishlistfragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saleschecker.databinding.FragmentWishListBinding
import com.example.saleschecker.mutual.*
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
        gameListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

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
            gameListAdapter.submitList(it/*.sortedByDescending { game -> game.discount_pct }*/)
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

//    private fun addSortingBtnClickListener() {
//        binding.sortingText.setOnClickListener {
//            viewModel.sorting.value = when (viewModel.sorting.value) {
//                Sorting.NAME -> Sorting.DISCOUNT
//                Sorting.DISCOUNT -> Sorting.PRICE
//                else -> Sorting.NAME
//            }
//            (it as TextView).text = "Sorting : ${ viewModel.sorting.value }"
//        }
//
//    }

    companion object {
        internal var backButtonRecyclerSavedState: Parcelable? = null
    }
}