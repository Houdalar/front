package tn.esprit.front.models

import androidx.annotation.DrawableRes



data class PlayList (

    var name: String? = null,
    @DrawableRes
    var cover: Int? = null,
//    var songs: List<Song>? = null,
    var owner: User? = null,
    var tracks: List<Tracks>? = null
    )