package com.irfan.nanamyuk.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tanah(
    var nama: String,
    var deskripsi: String,
    var urlPhoto: String
) : Parcelable

