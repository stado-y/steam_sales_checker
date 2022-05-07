package com.example.saleschecker.mutual

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

const val TAG = "FragmentWithRecycler"
const val RECYCLER_SAVE_STATE = "recycler_save_state"

abstract class FragmentWithRecycler: Fragment() {

    protected var savedRecyclerState: Parcelable? = null

    protected var recycler: RecyclerView? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        saveRecyclerState(outState)
        Log.e(TAG, "onSaveInstanceState: ", )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        onRestoreState(savedInstanceState)
        Log.e(TAG, "onViewStateRestored: ", )
    }

    protected fun saveRecyclerState(outState: Bundle) {
        val state = recycler?.layoutManager?.onSaveInstanceState()
        state?.let {
            outState.putParcelable(RECYCLER_SAVE_STATE, state)
            Log.e(TAG, "saveRecyclerState: $state", )
        }
    }

    protected fun onRestoreState(outState: Bundle?) {
        val state: Parcelable? = outState?.getParcelable(RECYCLER_SAVE_STATE)
        state?.let {
            savedRecyclerState = it
            Log.e(TAG, "onRestoreState: $state", )
        }
    }

    protected fun restoreRecyclerState() {
        savedRecyclerState?.let {
            recycler?.layoutManager?.onRestoreInstanceState(it)
        }
        savedRecyclerState = null
    }
}