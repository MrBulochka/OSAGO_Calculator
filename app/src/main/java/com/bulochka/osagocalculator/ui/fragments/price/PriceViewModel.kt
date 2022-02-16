package com.bulochka.osagocalculator.ui.fragments.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulochka.osagocalculator.data.model.OffersResponse
import com.bulochka.osagocalculator.data.model.SendCoefficients
import com.bulochka.osagocalculator.repository.CoefficientsRepository
import kotlinx.coroutines.launch

class PriceViewModel(private val coefficientsRepository: CoefficientsRepository) : ViewModel() {

    private val _coefficientsVisibility = MutableLiveData(false)
    val coefficientsVisibility: LiveData<Boolean> = _coefficientsVisibility

    private val _offersResponse = MutableLiveData<OffersResponse>()
    val offersResponse: LiveData<OffersResponse> = _offersResponse

    fun setCoefficientsState(isOpen: Boolean) {
        _coefficientsVisibility.value = isOpen
    }

    fun postCoefficients(coefficients: SendCoefficients) {
        viewModelScope.launch {
            _offersResponse.value = coefficientsRepository.postCoefficients(coefficients)
        }
    }
}