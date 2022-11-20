package tn.esprit.front.models

import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class Baby(
    @SerializedName("babyName")
    var name:String?=null,
    @SerializedName("birthday")
    var birthday: String?=null,
    @DrawableRes
    var babyPic: Int?=null,
    var email:String?=null

)
