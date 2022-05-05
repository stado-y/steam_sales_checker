package com.example.saleschecker.dialog.currency

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.saleschecker.R
import com.example.saleschecker.utils.ResourceProvider


class CurrencyDialogAdapter(
    myContext: Context,
    currencies: Array<String>,
    private val resourceProvider: ResourceProvider,
    itemLayout: Int = R.layout.currency_dialog_item_layout,
    private val textView: Int = R.id.currency_dialog_textview,
    private val imageView: Int = R.id.currency_dialog_imageview,
) : ArrayAdapter<String>(
    myContext,
    itemLayout,
    textView,
    currencies,
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(textView)
        val imageView = view.findViewById<ImageView>(imageView)
        val icon = resourceProvider.getCurrencyDrawable(textView.text.toString())
        imageView.setImageDrawable(icon)
        //textView.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        return view
    }
}