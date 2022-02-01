package com.bulochka.osagocalculator.ui.fragments.bottom_sheet

import androidx.lifecycle.*
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.repository.Repository
import com.bulochka.osagocalculator.ui.fragments.coefficients.CoefficientsViewModel
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val repository: Repository): ViewModel() {

    fun updateAllData(listData: List<Data>) {
        viewModelScope.launch {
            repository.updateAllData(listData)
        }
    }
}

class BottomSheetViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomSheetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BottomSheetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}