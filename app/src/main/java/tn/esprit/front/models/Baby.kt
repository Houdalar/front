package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class Baby(
    @SerializedName("babyName")
    var babyName:String?=null,
    @SerializedName("birthday")
    var birthday: String?=null,
    @SerializedName("babyPic")
    var babyPic: String?=null,
    @SerializedName("parent")
    var parent:String?=null,
    var token:String?=null,
    @SerializedName("gender")
    var gender:String?=null,
    )
