package com.bulochka.osagocalculator.ui.fragments.coefficients

import android.util.Log
import androidx.lifecycle.*
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.data.model.SendData
import com.bulochka.osagocalculator.repository.CoefficientsRepository
import kotlinx.coroutines.launch

class CoefficientsViewModel(private val coefficientsRepository: CoefficientsRepository) : ViewModel() {

    val coefficients: LiveData<List<Coefficient>> = coefficientsRepository.coefficients.asLiveData()
    val data: LiveData<List<Data>> = coefficientsRepository.data.asLiveData()

    private val _coefficientsVisibility = MutableLiveData(false)
    val coefficientsVisibility: LiveData<Boolean> = _coefficientsVisibility

    fun setCoefficientsState(isOpen: Boolean) {
        _coefficientsVisibility.value = isOpen
    }

    fun postData(data: SendData) {
        viewModelScope.launch {
            try {
                val result = coefficientsRepository.postData(data)
                updateCoefficients(result.factors)
            } catch (e: Exception) {

            }
        }
    }

    fun updateAllData(listData: List<Data>) {
        viewModelScope.launch {
            coefficientsRepository.updateAllData(listData)
        }
    }

    private fun updateCoefficients(coefficients: List<Coefficient>) {
        viewModelScope.launch {
            coefficientsRepository.updateAllCoefficients(coefficients)
        }
    }
}