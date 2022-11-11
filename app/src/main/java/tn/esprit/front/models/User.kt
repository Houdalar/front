package tn.esprit.front.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("name")
    var name: String? = null,

)
