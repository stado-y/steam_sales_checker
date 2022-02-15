package com.example.saleschecker.webviewfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.saleschecker.databinding.FragmentSteamAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SteamAuthFragment : Fragment(), UserIdReceiver {

    private lateinit var binding: FragmentSteamAuthBinding

    private val LOL_PARAM = "sasels_check"

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
                "openid.realm=https://$LOL_PARAM&" +
                "openid.return_to=https://$LOL_PARAM/signin/"


        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = SteamAuthWebViewClient(LOL_PARAM, this)
        binding.webView.loadUrl(webUrl)


    }

    override fun receiveUserId(userId: String) {

    }
}