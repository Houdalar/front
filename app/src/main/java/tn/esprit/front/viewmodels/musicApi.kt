package tn.esprit.front.viewmodels
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import tn.esprit.front.models.*
interface musicApi
{
    @GET("/media/getTracks")
    fun getTracks(): Call<ArrayList<Tracks>?>?
    @PUT("/media/list>ened")
    fun countListened(@Body name: String):Call<Tracks>
    @GET("/media/mostListened")
    fun getMostListenedTracks (@Body user: User ):Call<List<Tracks>>
    @GET("/media/newest")
    fun getNewestTracks (@Body user: User):Call<List<Tracks>>
    @POST("/media/getTrackByName")
    fun getTrackByName (@Body user: User):Call<Tracks>

    @POST("/user/music/addPlaylist")
    fun addPlayListToUser():Call<PlayList>
    @POST("/user/music/getPlaylists")
    fun getPlaylists(@Body name: String):Call<List<PlayList>>
    @POST("/user/music/getPlaylistTracks")
    fun getPlaylistTracks (@Body user: User ):Call<PlayList>
    @PUT("/user/music/addTrackToPlayList")
    fun addTrackToPlayList (@Body user: User):Call<PlayList>
    @DELETE("/user/music/deleteTrackfromPlaylist")
    fun deleteTrackfromPlaylist (@Body user: User):Call<PlayList>


    companion object {

        fun create() : musicApi {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.16.6.183:8080")
                .build()

            return retrofit.create(musicApi::class.java)
        }
    }
}