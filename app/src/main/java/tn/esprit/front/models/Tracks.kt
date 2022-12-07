package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Tracks (
    @SerializedName("name")
    var name: String,
    @SerializedName("artist")
    var artist: String? = null,
    @SerializedName("cover")
    var cover: String? = null,
    @SerializedName("duration")
    var duration: String? = null,
    @SerializedName("listened")
    var listened: Int? = null,
    @SerializedName("date")
    var date: String? = null,
    var token : String? = null,
    var url : String? = null

)

data class tracksResult (

    @SerializedName("sponsors")
    var tracks: MutableList<Tracks> ?= null

    )