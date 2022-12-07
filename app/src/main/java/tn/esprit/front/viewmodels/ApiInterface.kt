package tn.esprit.front.viewmodels

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import tn.esprit.front.models.*

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
    @POST("/user/verifyEmail")
    fun verifyEmail (@Body user: User):Call<User>


    @POST("/user/baby/addBaby")
    fun addBaby(@Body map : HashMap<String, String>) : Call<Baby>

    @POST("user/baby/getbabylist")
    fun getBabyList(@Body map : HashMap<String, String> ) :Call<MutableList<Baby>>

    @POST("/user/baby/getBaby")
    fun getBaby(@Body baby:Baby):Call<Baby>

    @POST("/user/baby/addHeight")
    fun addHeight(@Body map : HashMap<String, String>) : Call<Height>

    @POST("user/baby/getbabyheights")
    fun getBabyHeights(@Body map : HashMap<String, String>) :Call<MutableList<Height>>

    @POST("/user/baby/addWeight")
    fun addWeight(@Body map : HashMap<String, String>) : Call<Weight>

    @POST("user/baby/getbabyweights")
    fun getBabyWeights(@Body map : HashMap<String, String>) :Call<MutableList<Weight>>

    @POST("/user/baby/addVaccine")
    fun addVaccine(@Body map : HashMap<String, String>) : Call<Vaccine>

    @POST("user/baby/getbabyvaccines")
    fun getBabyVaccines(@Body map : HashMap<String, String>) :Call<MutableList<Vaccine>>


    companion object {

        fun create() : ApiInterface {

            println("ApiInterface")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.17.8.117:8080")
                .build()


            return retrofit.create(ApiInterface::class.java)
        }
    }
}