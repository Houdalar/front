package tn.esprit.front.Ui.PlayList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.Tracks
import tn.esprit.front.models.tracksResult
import tn.esprit.front.viewmodels.musicApi


class Topmusic : Fragment() {
    lateinit var recylcersong: RecyclerView
    lateinit var recylcersongAdapter: songviewadapter
    var tracks: ArrayList<Tracks> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        recylcersong = view.findViewById(R.id.recyclerplaylist)
        //preference= requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //val token = preference.getString("token", "")
       var services = musicApi.create()



        services.getTracks()!!.enqueue(object : Callback<MutableList<Tracks>>{
            override fun onResponse(call: Call<MutableList<Tracks>>, response: Response<MutableList<Tracks>>) {
                if (response.code()==200) {


                    val tracksList = response.body() as MutableList<Tracks>
                    tracks.addAll(tracksList)

                    recylcersongAdapter = songviewadapter(tracks)
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
       /* tracks.add(Tracks("morning","itchigo",cover= R.drawable.cover2,duration = "3:00", url = "http://172.17.3.247:8080/media/music/aot.mp3"))
        tracks.add(Tracks("vogel im kamfig","arima" ,cover=R.drawable.cover3 ,duration = "3:00", url = "http://172.17.3.247:8080/media/music/Black Clover - Opening 6 (HD).mp3"))
        tracks.add(Tracks("to destroy the evil ","kimetsu no yaiba" ,cover=R.drawable.cover4,duration = "3:00", url = "http://172.17.3.247:8080/media/music/black Clover 5.mp3"))

        recylcersongAdapter = songviewadapter(tracks)
        recylcersong.adapter = recylcersongAdapter
        recylcersong.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL, false)*/
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Topmusic().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
