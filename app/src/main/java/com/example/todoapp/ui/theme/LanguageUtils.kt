package com.example.todoapp.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.Locale
object LanguageUtils {
    // context ,     ar , en.
    // Shared Preference ->
    // Save Key - Value Pairs
    // fetch values by using keys
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)

        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        //En -> Ar
        //Context
        val sharedPreferences =
            context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(Constants.LANGUAGE_KEY, language)
        }
        return context.createConfigurationContext(configuration)
    }





}