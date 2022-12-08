package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Consultation(
    @SerializedName("babyId")
    var babyId: String?=null,
    @SerializedName("doctor")
    var doctor: String?=null,
    @SerializedName("date")
    var date: String?=null,
    @SerializedName("time")
    var time: String?=null,
    var token:String?=null,
    var babyName:String?=null,
)
