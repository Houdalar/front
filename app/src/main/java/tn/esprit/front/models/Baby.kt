package tn.esprit.front.models

import android.widget.ImageView
import com.google.gson.annotations.SerializedName

data class Baby(
    @SerializedName("babyName")
    var name:String,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("babyPic")
    var babyPic: ImageView
)
