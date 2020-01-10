package com.example.natandroid.dev.http.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

abstract class BaseResponse(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: String,
    @SerializedName("message") val message: String
)