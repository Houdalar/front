package tn.esprit.front.viewmodels

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import tn.esprit.front.models.*


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

    @POST("/user/music/addPlaylist")
    fun addPlayListToUser(@Body hashMap: HashMap<String, String>):Call<PlayList>
    @POST("/user/music/getPlaylists")
    fun getPlaylists(@Body hashMap: HashMap<String, String>):Call<MutableList<PlayList>>
    @POST("/user/music/getPlaylistTracks")
    fun getPlaylistTracks (@Body hashMap: HashMap<String, String>):Call<MutableList<Tracks>>
    @PUT("/user/music/addTrackToPlayList")
    fun addTrackToPlayList (@Body hashMap: HashMap<String, String>):Call<PlayList>
    @PUT("/user/music/deleteTrackfromPlaylist")
    fun deleteTrackfromPlaylist (@Body user: User):Call<PlayList>


    companion object {

        fun create() : musicApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080")
                .build()

            return retrofit.create(musicApi::class.java)
        }
    }
}
