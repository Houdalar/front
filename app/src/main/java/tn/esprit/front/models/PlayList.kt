package tn.esprit.front.models

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName


data class PlayList (

    @SerializedName("name")
    var name: String? = null,
    @SerializedName("cover")
    var cover: String? = null,
    @SerializedName("owner")
    var owner: String? = null,
    var id: String? = null,
    @SerializedName("tracks")
    var tracks: List<Tracks>? = null

    )