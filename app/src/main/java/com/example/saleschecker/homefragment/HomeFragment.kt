package com.example.saleschecker.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.fragment.findNavController
import com.example.saleschecker.R
import com.example.saleschecker.data.local.UserEntity
import com.example.saleschecker.data.network.steam.SteamRepository
import com.example.saleschecker.databinding.FragmentHomeBinding
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val userId = 76561198068107683

    @Inject lateinit var repository: SteamRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            repository.saveUserId(UserEntity(userId))
        }


        val text = binding.stubGoToWishlist.inflate()

        text.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWishListFragment())
        }
    }

}