package tn.esprit.front.viewmodels

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import tn.esprit.front.models.User

interface ApiInterface {

    @POST("/user/login")
    fun login(@Body user: User):Call<User>
    @POST("/user/signup")
    //sign up with 3 arguments
    fun signup(@Body user: User):Call<User>
    @POST("/user/patchOnce")
    fun updatePassword (@Body map: HashMap<String ,String>):Call<User>

    companion object {

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.16:8080")
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}