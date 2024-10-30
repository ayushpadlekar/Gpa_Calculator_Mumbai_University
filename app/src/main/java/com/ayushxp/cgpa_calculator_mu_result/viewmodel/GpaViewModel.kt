package com.ayushxp.cgpa_calculator_mu_result.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushxp.cgpa_calculator_mu_result.data.model.GpaEntity
import com.ayushxp.cgpa_calculator_mu_result.data.repository.GpaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class GpaViewModel @Inject constructor(private val repository: GpaRepository) : ViewModel() {

    val allGPA: LiveData<List<GpaEntity>> = repository.allGPA

    private val _semesters = mutableStateListOf("", "", "", "", "", "", "", "")
    private val _cgpa = MutableLiveData<Float?>()
    private val _percent = MutableLiveData<Float?>()

    val semesters: List<String> get() = _semesters
    val cgpa: LiveData<Float?> get() = _cgpa
    val percent: LiveData<Float?> get() = _percent

    fun updateSemester(index: Int, value: String) {
        _semesters[index] = value
    }

    private fun filterValidSgpas(semesters: List<Float?>): List<Float> {
        return semesters.filterNotNull().filter { it in 1f..10f }
    }

    fun calculateCGPA(semesters: List<Float?>) {
        val validSgpas = filterValidSgpas(semesters)
        _cgpa.value = if (validSgpas.isNotEmpty()) {
            round(validSgpas.average().toFloat() * 100) / 100
        } else null
    }

    fun calculatePercentage(semesters: List<Float?>) {
        val validSgpas = filterValidSgpas(semesters)
        _percent.value = if (validSgpas.isNotEmpty()) {
            round(validSgpas.average().toFloat() * 10f * 10) / 10
        } else null
    }

    fun reset() {
        _cgpa.value = null
        _percent.value = null
        _semesters.clear()
        _semesters.addAll(List(8) { "" })
    }

    fun saveGPA(
        sem1: Float?, sem2: Float?, sem3: Float?, sem4: Float?, sem5: Float?, sem6: Float?, sem7: Float?, sem8: Float?,
        cgpa: Float, percent: Float, dateTime: String? = null, id: Int? = null
    ) {
        val currentTime = dateTime ?: SimpleDateFormat("dd/MM/yyyy  hh:mm a", Locale.getDefault()).format(Date())
        val gpaEntity = GpaEntity(id ?: 0, sem1, sem2, sem3, sem4, sem5, sem6, sem7, sem8, cgpa, percent, currentTime)
        viewModelScope.launch {
            repository.insertGPA(gpaEntity)
        }
    }


    fun deleteGPA(gpa: GpaEntity) {
        viewModelScope.launch {
            repository.deleteGPA(gpa)
        }
    }
}

