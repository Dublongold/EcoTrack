package com.ecohabit.ecotrack.di

import androidx.room.Room
import com.ecohabit.ecotrack.data.store.dataStore
import com.ecohabit.ecotrack.ui.MainViewModel
import com.ecohabit.ecotrack.ui.checker.CheckerViewModel
import com.ecohabit.ecotrack.ui.dashboard.DashboardViewModel
import com.ecohabit.ecotrack.data.database.EcoDatabase
import com.ecohabit.ecotrack.data.food.HealthyFoodProvider
import com.ecohabit.ecotrack.data.quiz.QuizProvider
import com.ecohabit.ecotrack.data.store.VisitingStreakProvider
import com.ecohabit.ecotrack.ui.quiz.QuizViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Room Setup
    single {
        Room.databaseBuilder(androidContext(), EcoDatabase::class.java, "ecotrack_db")
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single {
        HealthyFoodProvider(androidContext())
    }
    single {
        QuizProvider(androidContext())
    }
    single { get<EcoDatabase>().ecoDao() }

    // DataStore Setup
    single { androidContext().dataStore }
    single { VisitingStreakProvider(get()) }

    // ViewModels
    viewModelOf(::MainViewModel)
    viewModelOf(::CheckerViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::QuizViewModel)
}
