package com.example.oguns.lab1

import android.content.Context
import org.jetbrains.anko.defaultSharedPreferences

class Util {
    companion object {
        val TIP_KEY = "DEFAULT_TIP"
        val CURRENCY_KEY = "DEFAULT_CURRENCY"

        fun updateTipPercentage(context: Context, percentage: Double) {
            val sharedPreferences = context.defaultSharedPreferences
            with(sharedPreferences.edit()) {
                putString(TIP_KEY, percentage.toString())
                apply()
            }
        }

        fun updateCurrency(context: Context, currency: String) {
            val sharedPreferences = context.defaultSharedPreferences
            with(sharedPreferences.edit()) {
                putString(CURRENCY_KEY, currency)
                apply()
            }
        }

        fun getTipPercentage(context: Context): String {
            val sharedPreferences = context.defaultSharedPreferences
            return sharedPreferences.getString(TIP_KEY, "0.00")
        }

        fun getCurrency(context: Context): String {
            val sharedPreferences = context.defaultSharedPreferences
            return sharedPreferences.getString(CURRENCY_KEY, "$")
        }
    }
}