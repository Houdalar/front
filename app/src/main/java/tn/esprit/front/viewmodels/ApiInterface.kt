package tn.esprit.front.viewmodels

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import tn.esprit.front.models.Baby
import tn.esprit.front.models.User

interface ApiInterface {

    @POST("/user/login")
    fun login(@Body user: User):Call<User>
    @POST("/user/signup")
    fun signup(@Body user: User):Call<User>
    @POST("/user/resend")
    fun resendCode (@Body user: User ):Call<User>
    @PUT("/user/activate")
    fun activateAccount (@Body user: User):Call<User>
    @POST("/user/paswordforgot")
    fun resetPasswordEmail (@Body user: User):Call<User>
    @POST("/user/resetPassword")
    fun resetPassword (@Body user: User):Call<User>
    @POST("/user/verifyCode")
    fun verifyCode (@Body user: User):Call<User>


    @PUT("/user/baby/addBaby")
    fun addBaby(@Body baby: Baby) : Call<Baby>

    @POST("user/baby/getbabylist")
    fun getBabyList(@Body user: User) :Call<User>

    @POST("/user/baby/getBaby")
    fun getBaby(@Body baby:Baby):Call<Baby>









    companion object {

        fun create() : ApiInterface {

            println("ApiInterface")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.17.2.175:8080")
                .build()


            return retrofit.create(ApiInterface::class.java)
        }
    }
}

// "http://192.168.1.19:8080