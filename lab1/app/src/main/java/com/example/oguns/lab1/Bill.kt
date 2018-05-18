package com.example.oguns.lab1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bill(var amount: Double = 0.0, var tipPercentage: Double = 0.0, var payers: Int = 1,
                var total: Double = 0.0): Parcelable