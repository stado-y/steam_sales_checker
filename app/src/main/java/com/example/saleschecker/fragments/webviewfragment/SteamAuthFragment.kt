package com.example.saleschecker.fragments.webviewfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.saleschecker.databinding.FragmentSteamAuthBinding
import dagger.hilt.android.AndroidEntryPoint

const val WEB_PARAM = "sasels_check"

@AndroidEntryPoint
class SteamAuthFragment : Fragment(), UserIdReceiver {

    private lateinit var binding: FragmentSteamAuthBinding

    private val viewModel: SteamAuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSteamAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webUrl = "https://steamcommunity.com/openid/login?" +
                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.mode=checkid_setup&" +
                "openid.ns=http://specs.openid.net/auth/2.0&" +
                "openid.realm=https://$WEB_PARAM&" +
                "openid.return_to=https://$WEB_PARAM/signin/"


        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = SteamAuthWebViewClient(WEB_PARAM, this)
        binding.webView.loadUrl(webUrl)


    }

    override fun receiveSteamUserId(userId: String?) {
        try {
            userId?.let {
                viewModel.saveUser(it.toLong())
                findNavController().navigate(SteamAuthFragmentDirections.actionSteamAuthFragmentToWishListFragment())
                return
            }
        } catch(error: Exception) {
            Toast.makeText(activity, "Error saving user id : ${ error.message }", Toast.LENGTH_LONG).show()
            Log.e(TAG, "receiveSteamUserId: ", error)
        }
        findNavController().popBackStack()
    }
}