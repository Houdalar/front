package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("baby")
    var baby:ArrayList<Baby>?=null

)
