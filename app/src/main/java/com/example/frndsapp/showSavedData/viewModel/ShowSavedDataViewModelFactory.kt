package com.example.frndsapp.showSavedData.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.frndsapp.showSavedData.api.SavedDataRepository

class ShowSavedDataViewModelFactory(private val repository: SavedDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShowSavedDataViewModel(repository) as T
    }
}