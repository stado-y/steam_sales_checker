package com.example.saleschecker.dialog.currency

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.saleschecker.CurrencyChangeListener
import com.example.saleschecker.R
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject


const val TAG = "CurrencyDialogFragment"
@AndroidEntryPoint
class CurrencyDialogFragment : DialogFragment(), DialogInterface.OnClickListener {

    @Inject lateinit var resourceProvider: ResourceProvider

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.currency_dialog_title)
                setAdapter(
                    CurrencyDialogAdapter(
                        context,
                        resourceProvider.getAllCurrencies(),
                        resourceProvider,
                    ),
                    this@CurrencyDialogFragment
                )
//                setItems(R.array.currencies_all, this@CurrencyDialogFragment)
            }
            builder.create()
        } ?: throw IllegalStateException("No Activity")
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val currency = resourceProvider.getAllCurrencies()[which]
        Log.e(TAG, "onClick: currency : $currency'' which : $which", )
        (activity as CurrencyChangeListener).onCurrencyChanged(currency)
    }
}