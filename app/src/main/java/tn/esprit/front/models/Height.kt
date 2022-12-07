package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Height(
    @SerializedName("babyId")
    var babyId: String?=null,
    @SerializedName("height")
    var height: String?=null,
    @SerializedName("date")
    var date: String?=null,
    var token:String?=null,
    var babyName:String?=null,

    )
