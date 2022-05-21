package com.example.medicaleducation.feature_programs.presentation.main_screen

import androidx.lifecycle.*
import com.example.medicaleducation.common.Resource
import com.example.medicaleducation.feature_programs.domain.model.Program
import com.example.medicaleducation.feature_programs.domain.use_case.GetAllProgramsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramsListViewModel @Inject constructor(
    private val getAllProgramsUseCase: GetAllProgramsUseCase
) : ViewModel() {

    private val _response = MutableLiveData<List<Program>>()
    val response: LiveData<List<Program>>
        get() = _response

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        getAllPrograms()
    }

    private fun getAllPrograms() {
        getAllProgramsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _response.postValue(result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _error.postValue(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
            }

        }.launchIn(viewModelScope)
    }

}