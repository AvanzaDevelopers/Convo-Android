package com.hotel.theconvo.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


object SharedPrefsHelper {
    lateinit var sharedPreferences: SharedPreferences
        private set

    fun initialize(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
}