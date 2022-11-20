package tn.esprit.front.models

data class Song(

    var name: String? = null,
    var artist: String? = null,
    var cover: Int? = null,
    var path: String? = null,
    var playList: PlayList? = null
)


