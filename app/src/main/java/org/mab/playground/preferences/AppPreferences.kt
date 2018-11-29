package org.mab.playground.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log


/**
 * Created by bala on 01-11-2017.
 */
class AppPreferences(context: Context) {
    private var _sharedPrefs: SharedPreferences
    private var _prefsEditor: SharedPreferences.Editor

    private val APP_SHARED_PREFS: String = "org.mab.playground.preferences"
    private val VISITED_STRING = "$APP_SHARED_PREFS.visitedstring"

    private val TAG = AppPreferences::class.java.simpleName


    init {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
                Activity.MODE_PRIVATE)
        this._prefsEditor = _sharedPrefs.edit()
        this._prefsEditor.apply()
    }

    fun addModeVisitTrack(mode: String) {
        Log.d(TAG, "addModeVisitTrack : $mode")
        val string = getModeVisitTrack()
        _prefsEditor.putString(VISITED_STRING, string + mode)
        _prefsEditor.commit()
    }

    fun getModeVisitTrack(): String {
        return _sharedPrefs.getString(VISITED_STRING, null) ?: ""
    }
}