package tn.esprit.front.Ui.PlayList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.models.PlayList

class playlistTest : AppCompatActivity()
{
    lateinit var recylcerPlaylist: RecyclerView
    lateinit var recylcerPlaylistAdapter: PlayListViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_test)
        supportActionBar?.hide()

        recylcerPlaylist = findViewById(R.id.recyclerplaylist)

        var playList : MutableList<PlayList> = ArrayList()

        playList.add(PlayList("morning", R.drawable.cover2))
        playList.add(PlayList("disney", R.drawable.cover3))
        playList.add(PlayList("hi ", R.drawable.cover4))

        recylcerPlaylistAdapter = PlayListViewAdapter(playList)
        recylcerPlaylist.adapter = recylcerPlaylistAdapter
        recylcerPlaylist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}