package tn.esprit.front.models

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

const val NAME = "NAME"
const val PICTURE="PICTURE"

data class Baby(
    @SerializedName("babyName")
    var babyName:String?=null,
    @SerializedName("birthday")
    var birthday: String?=null,
    @SerializedName("babyPic")
    var babyPic: String?=null,
    @SerializedName("email")
    var email:String?=null

)
