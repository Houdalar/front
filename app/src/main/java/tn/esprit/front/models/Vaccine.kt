package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Vaccine(
    @SerializedName("babyId")
    var babyId: String?=null,
    @SerializedName("vaccine")
    var vaccine: String?=null,
    @SerializedName("date")
    var date: String?=null,
    var token:String?=null,
    var babyName:String?=null,
)
