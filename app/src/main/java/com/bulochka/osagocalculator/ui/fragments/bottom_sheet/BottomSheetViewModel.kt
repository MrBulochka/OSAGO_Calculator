package com.bulochka.osagocalculator.ui.fragments.bottom_sheet

import androidx.lifecycle.*
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.repository.CoefficientsRepository
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val coefficientsRepository: CoefficientsRepository): ViewModel() {

    fun updateAllData(listData: List<Data>) {
        viewModelScope.launch {
            coefficientsRepository.updateAllData(listData)
        }
    }
}