package com.example.aquario.data

import com.example.aquario.data.model.LoggedInUser
import com.example.aquario.data.Result
import com.example.aquario.utils.GlobalUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    suspend fun register(username: String, password: String): Result<LoggedInUser> {
        val result = dataSource.register(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        GlobalUser.id = loggedInUser.userId
        GlobalUser.email = loggedInUser.email
        GlobalUser.token = loggedInUser.token.toString()

    }
}