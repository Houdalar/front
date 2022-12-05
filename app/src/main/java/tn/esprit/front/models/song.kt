package tn.esprit.front.models

import androidx.annotation.DrawableRes
data class Song(

    var name: String? = null,
    var artist: String? = null,
    @DrawableRes
    var cover: Int? = null,
    var path: String? = null,
    var playList: PlayList? = null
)
