package com.ayushxp.cgpa_calculator_mu_result.di

import android.content.Context
import androidx.room.Room
import com.ayushxp.cgpa_calculator_mu_result.data.dao.GpaDao
import com.ayushxp.cgpa_calculator_mu_result.data.database.GpaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GpaDatabase {
        return Room.databaseBuilder(
            context,
            GpaDatabase::class.java,
            "gpa_database"
        ).build()
    }

    @Provides
    fun provideGpaDao(database: GpaDatabase): GpaDao {
        return database.gpaDao()
    }
}