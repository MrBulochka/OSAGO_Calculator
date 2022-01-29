package com.bulochka.osagocalculator.ui.fragments.coefficients.bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bulochka.osagocalculator.data.model.Data
import com.bulochka.osagocalculator.repository.Repository

class BottomSheetViewModel(private val repository: Repository): ViewModel() {

    private val _data = MutableLiveData<Data>()
    val data: LiveData<Data> get() = _data

}