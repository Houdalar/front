package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Height(
    @SerializedName("email")
    var email: String,
    @SerializedName("babyName")
    var babyName: String? = null,
    @SerializedName("height")
    var height: Number,



)
