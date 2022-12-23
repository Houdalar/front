package tn.esprit.front.viewmodels

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.front.models.AudioBook
import tn.esprit.front.models.PlayList
import tn.esprit.front.models.Tracks
import tn.esprit.front.models.User


interface AudioBookAPi
{
    @POST("/media/getAudioBookByCategory")
    fun getAudioBookByCategory(@Body hashMap: HashMap<String, String>): Call<MutableList<AudioBook>>
    @GET("/media/getNewestAudioBook")
    fun getNewestAudioBook():Call<MutableList<AudioBook>>
    @GET("/media/getTopRatedAudioBook")
    fun getTopRatedAudioBook():Call<MutableList<AudioBook>>
    @GET("/media/getTopAudioBook")
    fun getTopAudioBook():Call<MutableList<AudioBook>>
    @PUT("/media/updateRatingAudioBook")
    fun updateRatingAudioBook(@Body book :AudioBook ):Call<AudioBook>
    @PUT("/media/updateListenedAudioBook")
    fun updateListenedAudioBook(@Body hashMap: HashMap<String, String>):Call<AudioBook>






    @POST("/user/audiobook/getFavoritesbooks")
    fun getFavoritesbooks (@Body hashMap: HashMap<String, String>):Call<MutableList<AudioBook>>
    @PUT("/user/audiobook/addFavoritesbooks")
    fun addFavoritesbooks (@Body hashMap: HashMap<String, String>):Call<AudioBook>
    @PUT("/user/audiobook/removeFavoritesbooks")
    fun removeFavoritesbooks (@Body hashMap: HashMap<String, String>):Call<AudioBook>

    companion object {

        fun create() : AudioBookAPi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080")
                .build()

            return retrofit.create(AudioBookAPi::class.java)
        }
    }
}
