package com.doing.httptest.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CookieDao{

    @Query("SELECT * FROM cookie LIMIT 1")
    fun get(): CookieDb?

    /**
     * return: 删除的行数
     */
    @Delete
    fun remove(user: CookieDb? = get()): Int

    /**
     * return: rowId
     */
    @Insert
    fun add(user: CookieDb): Long

    /**
     * return: 影响的行数
     */
    @Update
    fun update(user: CookieDb): Int


    @Query("SELECT * FROM cookie LIMIT 1")
    fun getRx(): Single<MutableList<CookieDb>>

}