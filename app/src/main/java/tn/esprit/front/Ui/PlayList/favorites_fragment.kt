package tn.esprit.front.Ui.PlayList

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.Tracks
import tn.esprit.front.viewmodels.musicApi

class fav : Fragment() {
    lateinit var recylcersong: RecyclerView
    lateinit var recylcersongAdapter: favouritsong_adapter
    var tracks: ArrayList<Tracks> = ArrayList()
    lateinit var swipeRefreshLayout: SwipeRefreshLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lateinit var sharedPreferences: SharedPreferences
        val view = inflater.inflate(R.layout.fragment_fav, container, false)
        val token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDc0MTg1MH0.GPsTqD7vbaBS65dsUJdfbPcU0Zdh4kmH4i8irCWgP5M"
        recylcersong = view.findViewById(R.id.favorite_recycler)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        val map: HashMap<String, String> = HashMap()
        var services = musicApi.create()

        map["token"] = token

        services.getFavoritesTracks(map)!!.enqueue(object : Callback<MutableList<Tracks>> {
            override fun onResponse(call: Call<MutableList<Tracks>>, response: Response<MutableList<Tracks>>) {
                if (response.code()==200) {


                    val tracksList = response.body() as MutableList<Tracks>
                    // update the tracks list
                    tracks.clear()
                    tracks.addAll(tracksList)
                    // add the list to shared preferences
                    sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", 0)
                    val editor = sharedPreferences.edit()
                    val set: MutableSet<String> = HashSet()
                    set.addAll(tracksList.map { it.name })
                    editor.putStringSet("favoriteTracks", set)
                    editor.commit()
                    println(set)
                    recylcersongAdapter = favouritsong_adapter(tracks)
                    recylcersong.adapter = recylcersongAdapter
                    recylcersongAdapter.notifyDataSetChanged()
                    recylcersong.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL, false)
                }

                else
                {
                    Toast.makeText(context, "error ", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Tracks>>, t: Throwable)
            {
                Toast.makeText(context, "error while getting the tracks", Toast.LENGTH_SHORT).show()
            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            recylcersongAdapter.notifyDataSetChanged()
        }
        return  view

    }


}