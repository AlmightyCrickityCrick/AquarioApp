package com.example.aquario.utils

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.okHttpClient
import com.example.aquario.*
import com.example.aquario.data.model.AquariumDetail
import com.example.aquario.data.model.AquariumInfo
import com.example.aquario.data.model.LoggedInUser
import com.example.aquario.data.model.SensorInfo
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

    suspend fun getAquariums(): ArrayList<AquariumInfo>?{
        try {
            val response = authorizedApolloClient.query(GetAquariumsQuery()).execute()

            if (response.data?.aquariumId != null) {
                var aquariums = ArrayList<AquariumInfo>()
                for (a in response.data!!.aquariumId!!) {
                    if (a != null) {
                        aquariums.add(AquariumInfo(a.id,
                            a.aquariumId,
                            a.nickname
                        )
                        )
                    }
                }
                return aquariums
            }
        } catch (e: Throwable){
            print(e)
        }
        return null
    }

    suspend fun getAquariumDetails(id: String) : AquariumDetail?{
        try {
            var response =
                authorizedApolloClient.query(GetAquariumDetailsInfoQuery(id.toInt())).execute()
            if (response.data?.aquarium != null) {
                var det = response.data!!.aquarium
                if (det != null) {
                    return AquariumDetail(
                        det.id, det.nickname,
                        det.feedingTime as String,
                        det.waterLevel,
                        det.generalSystemState,
                        obtainSenzors(det.sensors)
                    )
                }
            }
        }catch (e : Throwable){
            print(e)
        }
        return null
    }

    private fun obtainSenzors(sensors: List<GetAquariumDetailsInfoQuery.Sensor>): ArrayList<SensorInfo>? {
        if (sensors.size == 0) return null
        else{
            var sen = ArrayList<SensorInfo>()
            for (s in sensors){
                sen.add(
                    SensorInfo(s.id, "type", s.currentValue.toInt(), 0,
                        s.currentTime as String
                    )
                )
            }
            return sen
        }
    }

    suspend fun registerAquarium(aquarium_id: String, nickname:String): String? {
        try {
            interceptHttp = OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor(token)).build()
            authorizedApolloClient = ApolloClient.Builder().serverUrl(URL).okHttpClient(interceptHttp).build()

            val response = authorizedApolloClient.mutation(RegisterAquariumMutation(
                aquarium_id= aquarium_id,
                nickname = nickname,
                fish_id = Optional.present(null),
                height = Optional.present(null),
                width = Optional.present(null),
                length = Optional.present(null),
                volume = Optional.present(null),
                feeding_time = Optional.present(null))).execute()
            print(response)
            if (response.data != null && response.data!!.registerAquarium !=null){
                return response.data!!.registerAquarium?.id
            }
        } catch (e : Throwable){
            print(e)
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
