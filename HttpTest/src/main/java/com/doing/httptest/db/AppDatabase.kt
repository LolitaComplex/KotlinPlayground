package com.doing.httptest.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [CookieDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): CookieDao

}