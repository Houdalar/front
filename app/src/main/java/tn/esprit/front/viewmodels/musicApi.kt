package tn.esprit.front.viewmodels

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.front.models.PlayList
import tn.esprit.front.models.Tracks
import tn.esprit.front.models.User


interface musicApi
{
    @GET("/media/getTracks")
    fun getTracks(): Call<MutableList<Tracks>>
    @PUT("/media/listened")
    fun countListened(@Body tracks: Tracks):Call<Tracks>
    @GET("/media/newest")
    fun getNewestTracks ():Call<MutableList<Tracks>>
    @GET("/media/getMediaCategory")
    fun getMediaCategory ():Call<MutableList<PlayList>>
    @POST("/media/getNextSongtop")
    fun getNextSongtop (@Body hashMap: HashMap<String, String>):Call<Tracks>
    @POST("/media/getNextSongnew")
    fun getNextSongnew (@Body hashMap: HashMap<String, String>):Call<Tracks>




    @POST("/user/music/getFavoritesTracks")
    fun getFavoritesTracks (@Body hashMap: HashMap<String, String>):Call<MutableList<Tracks>>
    @POST("/user/music/getNextFavoritesTracks")
    fun getNextFavoritesTracks(@Body hashMap: HashMap<String, String>):Call<Tracks>
    @POST("/user/music/getPreviousFavoritesTracks")
    fun getPreviousFavoritesTracks(@Body hashMap: HashMap<String, String>):Call<Tracks>
    @PUT("/user/music/removeFavoritesTrack")
    fun removeFavoritesTrack(@Body hashMap: HashMap<String, String>):Call<Tracks>
    @POST("/user/music/addFavoritesTrack")
    fun addFavoritesTrack(@Body hashMap: HashMap<String, String>):Call<Tracks>

    @Multipart
    @POST("/user/music/addPlaylist")
    fun addPlayListToUser(@Part cover: MultipartBody.Part,
                          @Part  name: MultipartBody.Part,
                          @Part ("token")token:String):Call<PlayList>
    @POST("/user/music/getPlaylists")
    fun getPlaylists(@Body hashMap: HashMap<String, String>):Call<MutableList<PlayList>>
    @POST("/user/music/getPlaylistTracks")
    fun getPlaylistTracks (@Body hashMap: HashMap<String, String>):Call<MutableList<Tracks>>
    @PUT("/user/music/addTrackToPlayList")
    fun addTrackToPlayList (@Body hashMap: HashMap<String, String>):Call<PlayList>
    @PUT("/user/music/deleteTrackfromPlaylist")
    fun deleteTrackfromPlaylist (@Body hashMap: HashMap<String, String>):Call<Tracks>
    @PUT("/user/music/deletePlayList")
    fun deletePlayList (@Body hashMap: HashMap<String, String>):Call<PlayList>

    companion object {

        fun create() : musicApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://web-production-d88c.up.railway.app/")
                .build()

            return retrofit.create(musicApi::class.java)
        }
    }
}
