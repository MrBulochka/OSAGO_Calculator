package com.bulochka.osagocalculator.ui.fragments.coefficients

import androidx.lifecycle.*
import com.bulochka.osagocalculator.data.model.Coefficient
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.repository.Repository

class CoefficientsViewModel(repository: Repository) : ViewModel() {

    val coefficients: LiveData<List<Coefficient>> = repository.coefficients.asLiveData()

    val data: LiveData<List<Data>> = repository.data.asLiveData()

    private val _coefficientsVisibility = MutableLiveData(false)
    val coefficientsVisibility: LiveData<Boolean> = _coefficientsVisibility

    fun setCoefficientsState(isOpen: Boolean) {
        _coefficientsVisibility.value = isOpen
    }
}


class CoefficientsViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoefficientsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoefficientsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}