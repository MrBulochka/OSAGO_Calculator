package com.bulochka.osagocalculator.ui.fragments.coefficients

import androidx.lifecycle.*
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.repository.CoefficientsRepository

class CoefficientsViewModel(coefficientsRepository: CoefficientsRepository) : ViewModel() {

    val coefficients: LiveData<List<Coefficient>> = coefficientsRepository.coefficients.asLiveData()

    val data: LiveData<List<Data>> = coefficientsRepository.data.asLiveData()

    private val _coefficientsVisibility = MutableLiveData(false)
    val coefficientsVisibility: LiveData<Boolean> = _coefficientsVisibility

    fun setCoefficientsState(isOpen: Boolean) {
        _coefficientsVisibility.value = isOpen
    }
}