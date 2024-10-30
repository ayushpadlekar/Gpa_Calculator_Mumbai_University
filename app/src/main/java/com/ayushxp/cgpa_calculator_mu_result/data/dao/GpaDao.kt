package com.ayushxp.cgpa_calculator_mu_result.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayushxp.cgpa_calculator_mu_result.data.model.GpaEntity

@Dao
interface GpaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGPA(gpaEntity: GpaEntity)

    @Delete
    suspend fun deleteGPA(gpaEntity: GpaEntity)

    @Query("SELECT * FROM gpa_table ORDER BY id DESC")
    fun getAllGPA(): LiveData<List<GpaEntity>>

    @Query("DELETE FROM gpa_table")
    suspend fun clearAll()
}
