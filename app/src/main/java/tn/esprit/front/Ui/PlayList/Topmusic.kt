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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //lateinit var preference: SharedPreferences
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        recylcersong = view.findViewById(R.id.recyclerplaylist)
        //preference= requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        //val token = preference.getString("token", "")
        var services = musicApi.create()



        services.getTracks()!!.enqueue(object : Callback<ArrayList<Tracks>?>{
            override fun onResponse(call: Call<ArrayList<Tracks>?>, response: Response<ArrayList<Tracks>?>) {
                if (response.code()==200) {

                    val songs : ArrayList<Tracks>
                    val tracksList : ArrayList<Tracks> = response.body()!!
                    for(track in tracksList){
                        songs.add(Tracks(name = track.name, artist = track.artist, cover = track.cover, duration = track.duration, listened = track.listened, date = track.date))
                    }

                    recylcersongAdapter = songviewadapter(tracksList)
                    recylcersong.adapter = recylcersongAdapter
                    recylcersong.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL, false)
                }

                else
                {
                    Toast.makeText(context, "error ", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ArrayList<Tracks>?>, t: Throwable)
            {
                Toast.makeText(context, "error while getting the tracks", Toast.LENGTH_SHORT).show()
            }
        })
       /* songs.add(Song("morning","itchigo", R.drawable.cover2))
        songs.add(Song("vogel im kamfig","arima" ,R.drawable.cover3))
        songs.add(Song("to destroy the evil ","kimetsu no yaiba" ,R.drawable.cover4))*/
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
