package com.example.aquario.data

import com.apollographql.apollo3.api.Optional
import com.example.aquario.data.model.LoggedInUser
import com.example.aquario.utils.ApolloClientService
import java.io.IOException
import com.example.aquario.data.Result

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            var token = ApolloClientService.login(username, password)
            if (token!=null){
                var user = ApolloClientService.getMe()

                if(user!= null){
                    user.token = token
                    return Result.Success(user)
                }
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
        return Result.Error(IOException("Error logging in"))
    }

    suspend fun getUserInfo(): Result<LoggedInUser> {
        var user = ApolloClientService.getMe()
        if(user!=null) return Result.Success(user)
        return Result.Error(IOException("Couldnt obtain user info"))

    }
    suspend fun register(username:String, password: String): Result<LoggedInUser> {
        try {
            var id = ApolloClientService.register(username,password)
            if(id!=null){
                return Result.Success(LoggedInUser(id, email = username, token ="null"))
            }
        } catch (e: Throwable) {
            print(e.stackTrace)
            return Result.Error(IOException("Error logging in", e))
        }
        return Result.Error(IOException("Error logging in"))

    }

    fun logout() {
        // TODO: revoke authentication
    }
}