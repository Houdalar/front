package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class AudioBook (
    @SerializedName("bookTitle")
    var bookTitle: String? = null,
    @SerializedName("Author")
    var Author: String? = null,
    @SerializedName("cover")
    var cover: String? = null,
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("Description")
    var Description: String? = null,
    @SerializedName("Rating")
    var Rating: Float? = null,
    @SerializedName("url")
    var url : String? = null,
    @SerializedName("listened")
    var listened: Int? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("_id")
    var id : String? = null,
    var fav : Boolean? = null,
    @SerializedName("duration")
    var duration: String? = null,
    @SerializedName("narrator")
    var narrator: String? = null,
    @SerializedName("language")
    var language: String? = null

) {
    val size: Int
        get() = 0
}
