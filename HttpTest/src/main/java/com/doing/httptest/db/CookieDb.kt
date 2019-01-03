package com.doing.httptest.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "cookie")
class CookieDb (id: String?, name: String, value: String, expiresAt: Long, domain: String, path: String,
                secure: Boolean, httpOnly: Boolean, persistent: Boolean, hostOnly: Boolean){

    constructor(name: String, value: String, expiresAt: Long, domain: String, path: String,
                secure: Boolean, httpOnly: Boolean, persistent: Boolean, hostOnly: Boolean):
            this(null, name, value, expiresAt, domain, path, secure, httpOnly, persistent, hostOnly)

    @PrimaryKey(autoGenerate=true)
    var id  = id

    val name = name
    val value = value
    val expiresAt = expiresAt
    val domain = domain
    val path = path
    val secure = secure
    val httpOnly = httpOnly

    val persistent = persistent // True if 'expires' or 'max-age' is present.
    val hostOnly = hostOnly // True unless 'domain' is present.


}