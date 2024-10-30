package com.ayushxp.cgpa_calculator_mu_result.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gpa_table")
data class GpaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sem1: Float?,
    val sem2: Float?,
    val sem3: Float?,
    val sem4: Float?,
    val sem5: Float?,
    val sem6: Float?,
    val sem7: Float?,
    val sem8: Float?,
    val cgpa: Float,
    val percent: Float,
    val dateTime: String
)
