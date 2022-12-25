package tn.esprit.front.viewmodels

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import tn.esprit.front.models.*

interface BabyAPIInterface {

    @Multipart
    @POST("/user/baby/addBaby")
    fun addBaby(
        //@Body map : HashMap<String, String>
        @Part("babyName") babyName: String,
        @Part("birthday") birthday: String,
        @Part babyPic: MultipartBody.Part,
        @Part("token") token: String,
    ) : Call<Baby>

    @POST("user/baby/getbabylist")
    fun getBabyList(@Body map : HashMap<String, String> ) : Call<MutableList<Baby>>

    @POST("/user/baby/getBaby")
    fun getBaby(@Body baby: Baby): Call<Baby>

    @POST("/user/baby/addHeight")
    fun addHeight(@Body map : HashMap<String, String>) : Call<Height>

    @POST("user/baby/getbabyheights")
    fun getBabyHeights(@Body map : HashMap<String, String>) : Call<MutableList<Height>>

    @POST("/user/baby/addWeight")
    fun addWeight(@Body map : HashMap<String, String>) : Call<Weight>

    @POST("user/baby/getbabyweights")
    fun getBabyWeights(@Body map : HashMap<String, String>) : Call<MutableList<Weight>>

    @POST("/user/baby/addVaccine")
    fun addVaccine(@Body map : HashMap<String, String>) : Call<Vaccine>

    @POST("user/baby/getbabyvaccines")
    fun getBabyVaccines(@Body map : HashMap<String, String>) : Call<MutableList<Vaccine>>

    @POST("/user/baby/addConsultation")
    fun addConsultation(@Body map : HashMap<String, String>) : Call<Consultation>

    @POST("user/baby/getDoctorConsultations")
    fun getDoctorConsultations(@Body map : HashMap<String, String>) : Call<MutableList<Consultation>>


    companion object {

        fun create() : BabyAPIInterface {

            println("Baby_API_Interface")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.16:8080")
                .build()


            return retrofit.create(BabyAPIInterface::class.java)
        }
    }
}