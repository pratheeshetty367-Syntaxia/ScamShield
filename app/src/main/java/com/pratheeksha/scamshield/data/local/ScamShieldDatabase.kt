package com.pratheeksha.scamshield.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScamNumberEntity::class], version = 1, exportSchema = false)
abstract class ScamShieldDatabase : RoomDatabase() {
    abstract fun scamNumberDao(): ScamNumberDao
}