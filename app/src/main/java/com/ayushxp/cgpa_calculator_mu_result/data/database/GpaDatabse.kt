package com.ayushxp.cgpa_calculator_mu_result.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayushxp.cgpa_calculator_mu_result.data.dao.GpaDao
import com.ayushxp.cgpa_calculator_mu_result.data.model.GpaEntity

@Database(entities = [GpaEntity::class], version = 1, exportSchema = false)
abstract class GpaDatabase : RoomDatabase() {
    abstract fun gpaDao(): GpaDao
}
