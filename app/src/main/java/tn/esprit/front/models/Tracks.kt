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
    var _id : String? = null,
    @SerializedName("url")
    var url : String? = null,
    var fav : Boolean? = null,
    @SerializedName("category")
    var category: String? = null,

) {
    val size: Int
        get() = 0
}
