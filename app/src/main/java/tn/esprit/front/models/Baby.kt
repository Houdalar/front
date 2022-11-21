package tn.esprit.front.models

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

const val NAME = "NAME"
const val PICTURE="PICTURE"

data class Baby(
    @SerializedName("babyName")
    var name:String?=null,
    @SerializedName("birthday")
    var birthday: String?=null,
    @DrawableRes
    var babyPic: Int?=null,
    var email:String?=null

)
