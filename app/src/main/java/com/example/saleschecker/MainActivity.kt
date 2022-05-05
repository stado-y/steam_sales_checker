package com.example.saleschecker

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.saleschecker.databinding.ActivityMainBinding
import com.example.saleschecker.dialog.currency.CurrencyDialogFragment
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "MainActivity"

interface CurrencyChangeListener {
    fun onCurrencyChanged(currency: String)
}



@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CurrencyChangeListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject lateinit var resourceProvider: ResourceProvider

    private var currencyMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        currencyMenuItem = menu?.findItem(R.id.menu_currency)

        observeCurrency()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_currency -> {
                currencyMenuItem = item
                openCurrencyDialog()
            }
        }
        return true
    }

    private fun observeCurrency() {
        viewModel.currentCountryCode.observe(this) {
            changeCurrencyPicture(it)
        }
    }

    private fun openCurrencyDialog() {
        val dialogFragment = CurrencyDialogFragment()
        dialogFragment.show(supportFragmentManager, "currency")
    }

    private fun changeCurrencyPicture(countryCode: String?) {
        if (currencyMenuItem == null) {
            Log.e(TAG,"changeCurrencyPicture : currencyMenuItem is null")
        }
        Log.e(TAG, "changeCurrencyPicture: countryCode : $countryCode", )
        val flagDrawable = resourceProvider.getCountryCodeDrawable(countryCode)
        currencyMenuItem?.icon = flagDrawable

    }

    override fun onCurrencyChanged(currency: String) {
        val countryCode = resourceProvider.getCountryCodeFromCurrency(currency)
        Log.e(TAG, "onCurrencyChanged: countryCode : $countryCode", )
        viewModel.setCurrency(countryCode)
    }

}