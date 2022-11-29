package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Weight(
    @SerializedName("email")
    var email: String,
    @SerializedName("babyName")
    var babyName: String? = null,
    @SerializedName("weight")
    var weight: Number,
)
