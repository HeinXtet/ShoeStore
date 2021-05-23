package com.deevvdd.shoestore.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.shoestore.model.ShoeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val shoeLiveData = MutableLiveData<ArrayList<ShoeModel>>()
    var randomNumber = 1
    private val shoe = MutableLiveData<ShoeModel>(ShoeModel())
    val isValidToCreate = Transformations.map(shoe) {
        Timber.d("Change ${it}")
        !it?.company.isNullOrEmpty() && !it?.title.isNullOrEmpty()
    }

    fun updateShoeName(name: String) {
        shoe.value = shoe.value?.copy(title = name)
    }

    fun updatePrize(prize: String) {
        if (prize.isNotEmpty()) {
            shoe.value = shoe.value?.copy(prize = prize.toDouble())
        }
    }

    fun updateCompany(company: String) {
        shoe.value = shoe.value?.copy(company = company)
    }

    fun updateStock(stock: String) {
        if (stock.isNotEmpty()) {
            shoe.value = shoe.value?.copy(quantity = stock.toInt())
        }
    }


    fun addNewShoe() {
        randomNumber += randomNumber
        val previousList = shoeLiveData.value ?: ArrayList()
        val shoe =
            shoe.value?.copy(imageUrl = "https://picsum.photos/200/300?random?Shoe=${randomNumber}")
        previousList.add(shoe!!)
        shoeLiveData.value = previousList
    }
}