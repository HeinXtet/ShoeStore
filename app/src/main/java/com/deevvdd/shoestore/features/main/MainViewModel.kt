package com.deevvdd.shoestore.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.shoestore.model.ShoeModel
import com.deevvdd.shoestore.utils.DoubleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val shoeLiveData = MutableLiveData<ArrayList<ShoeModel>>()

    init {
        shoeLiveData.value = ArrayList()
    }

    var randomNumber = 1

    val shoeName = MutableLiveData<String>()
    val company = MutableLiveData<String>()
    val prize = MutableLiveData<String>()
    val quantity = MutableLiveData<String>()

    private val _shoeCreated = MutableLiveData<Boolean>()
    private val checkRequiredFields = DoubleObserver(shoeName, company)


    val shoes = Transformations.map(shoeLiveData) {
        it
    }

    val isEmptyShoes = Transformations.map(shoeLiveData) {
        it.isEmpty()
    }


    val shoeCreated: LiveData<Boolean>
        get() {

            return _shoeCreated
        }

    fun updateShoeCreated() {
        _shoeCreated.value = false
    }


    val isValidToCreate = Transformations.map(checkRequiredFields) {
        !it.first.isNullOrEmpty() && !it.second.isNullOrEmpty()
    }


    fun addNewShoe() {
        Timber.d("Create Shoe ")
        randomNumber += randomNumber
        val previousList = shoeLiveData.value ?: ArrayList()
        val shoe = ShoeModel(
            title = shoeName.value ?: "",
            company = company.value ?: "",
            prize = (prize.value ?: "0").toDouble(),
            quantity = (quantity.value ?: "0").toInt(),
            imageUrl = "https://picsum.photos/200/300?random?Shoe=${randomNumber}"
        )
        previousList.add(shoe)
        shoeLiveData.value = previousList
        shoeName.value = ""
        company.value = ""
        prize.value = ""
        quantity.value = ""
        _shoeCreated.value = true
    }
}