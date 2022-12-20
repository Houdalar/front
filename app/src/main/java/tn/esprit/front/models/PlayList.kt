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
    var _id: String? = null,
    @SerializedName("tracks")
    // a list ob object id mongoose
    var tracks: List<String>? = null,



    )