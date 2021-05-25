package com.deevvdd.shoestore.utils

import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.deevvdd.shoestore.model.ShoeModel
import timber.log.Timber

@BindingAdapter(value = ["shoes"], requireAll = false)
fun loadShoeCard(view: LinearLayout, shoes: LiveData<ArrayList<ShoeModel>>?) {
    Timber.d("Shoe Binding ${shoes?.value?.size}")
    val shoesData = shoes?.value ?: ArrayList()
    view.removeAllViews()
    shoesData.mapIndexed { index, shoeModel ->
        UI.generateShoeCard(
            view,
            shoeModel,
            index == shoesData.size - 1,
            view.context
        )
    }
}
