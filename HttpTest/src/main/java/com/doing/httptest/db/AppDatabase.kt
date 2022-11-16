package com.doing.httptest.db


//@Database(entities = [CookieDb::class], version = 1)
abstract class AppDatabase /*: RoomDatabase()*/ {

    abstract fun userDao(): CookieDao

}