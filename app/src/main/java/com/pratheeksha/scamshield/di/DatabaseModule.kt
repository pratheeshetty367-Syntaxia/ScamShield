package com.pratheeksha.scamshield.di

import android.content.Context
import androidx.room.Room
import com.pratheeksha.scamshield.data.local.ScamNumberDao
import com.pratheeksha.scamshield.data.local.ScamShieldDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): ScamShieldDatabase {
        return Room.databaseBuilder(
            context,
            ScamShieldDatabase::class.java,
            "scamshield_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScamNumberDao(database: ScamShieldDatabase): ScamNumberDao {
        return database.scamNumberDao()
    }
}