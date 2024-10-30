package com.ayushxp.cgpa_calculator_mu_result.data.repository

import androidx.lifecycle.LiveData
import com.ayushxp.cgpa_calculator_mu_result.data.dao.GpaDao
import com.ayushxp.cgpa_calculator_mu_result.data.model.GpaEntity
import javax.inject.Inject

class GpaRepository @Inject constructor(private val gpaDao: GpaDao) {
    val allGPA: LiveData<List<GpaEntity>> = gpaDao.getAllGPA()

    suspend fun insertGPA(gpaEntity: GpaEntity) {
        gpaDao.insertGPA(gpaEntity)
    }

    suspend fun deleteGPA(gpaEntity: GpaEntity) {
        gpaDao.deleteGPA(gpaEntity)
    }

    suspend fun clearAll() {
        gpaDao.clearAll()
    }
}
