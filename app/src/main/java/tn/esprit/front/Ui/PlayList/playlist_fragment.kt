package tn.esprit.front.Ui.PlayList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.models.PlayList


class Playlist : Fragment() {
    lateinit var recylcerPlaylist: RecyclerView
    lateinit var recylcerPlaylistAdapter: PlayListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        recylcerPlaylist = view.findViewById(R.id.recyclerplaylist)



        var playList : MutableList<PlayList> = ArrayList()

        playList.add(PlayList("morning", R.drawable.cover2))
        playList.add(PlayList("disney", R.drawable.cover3))
        playList.add(PlayList("hi ", R.drawable.cover4))

        recylcerPlaylistAdapter = PlayListViewAdapter(playList)
        recylcerPlaylist.adapter = recylcerPlaylistAdapter
        recylcerPlaylist.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        return view
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Playlist().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
