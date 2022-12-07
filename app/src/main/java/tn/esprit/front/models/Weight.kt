package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Weight(
    @SerializedName("email")
    var babyId: String,
    @SerializedName("weight")
    var weight: String,
    @SerializedName("date")
    var date: String,
)
