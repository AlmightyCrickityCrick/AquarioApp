package com.example.aquario.utils

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.aquario.CreateUserMutation
import com.example.aquario.GetMeQuery
import com.example.aquario.LoginMutation
import com.example.aquario.data.model.LoggedInUser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

object ApolloClientService {
    private val okHttpClient = OkHttpClient.Builder().build()
    private val URL = "https://aquario.herokuapp.com/graphql/"
    private val apolloClient = ApolloClient.Builder().serverUrl(URL).okHttpClient(okHttpClient).build()
    lateinit var authorizedApolloClient: ApolloClient
    lateinit var interceptHttp: OkHttpClient
    lateinit var token: String


    suspend fun register(username: String, password: String): String? {
        val response = apolloClient.mutation(CreateUserMutation(username, password)).execute()
        val x = response
        val id = response.data?.createUser?.id
        return id
    }

    suspend fun login(username:String, password:String): String? {
        val response = apolloClient.mutation(LoginMutation(username, password)).execute()
        val token = response.data?.login?.token
        if(token != null){
            interceptHttp = OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor(token)).build()
            authorizedApolloClient = ApolloClient.Builder().serverUrl(URL).okHttpClient(interceptHttp).build()
            this.token = token
        }

        return token
    }

    suspend fun getMe(): LoggedInUser? {
        interceptHttp = OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor(token)).build()
        authorizedApolloClient = ApolloClient.Builder().serverUrl(URL).okHttpClient(interceptHttp).build()
        val response = authorizedApolloClient.query(GetMeQuery()).execute()

        if(response.data!=null) {
            return response.data!!.me?.let {
                LoggedInUser(
                    it.user.id,
                    email = it.user.email,
                    token = this.token
                )
            }

        }
        return null
    }

    private class AuthorizationInterceptor(val token:String): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder().addHeader("Authorization", "JWT $token").build()

            return chain.proceed(request)
        }

    }
}
