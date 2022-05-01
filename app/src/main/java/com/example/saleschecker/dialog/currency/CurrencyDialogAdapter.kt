package com.example.saleschecker.dialog.currency

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.saleschecker.R
import com.example.saleschecker.utils.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton



class CurrencyDialogAdapter(
    myContext: Context,
    val currencies: Array<String>,
    private val resourceProvider: ResourceProvider,
) : ArrayAdapter<String>(
    myContext,
    R.layout.currency_dialog_item_layout,
    R.id.currency_dialog_textview,
    currencies,
) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.currency_dialog_textview)
        val imageView = view.findViewById<ImageView>(R.id.currency_dialog_imageview)
        val icon = resourceProvider.getCurrencyDrawable(textView.text.toString())
        imageView.setImageDrawable(icon)
        //textView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        return view
    }
}