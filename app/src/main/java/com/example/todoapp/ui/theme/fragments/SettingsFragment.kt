package com.example.todoapp.ui.theme.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.*
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Constants
import com.example.todoapp.ui.theme.LanguageUtils
import com.example.todoapp.ui.theme.MainActivity
import java.util.Locale

class SettingsFragment : Fragment() {

    lateinit var languageSpinner : Spinner
    lateinit var modeSpinner : Spinner
    lateinit var sharedPreferences: SharedPreferences
//    val languages = resources.getStringArray(R.array.languages)
//    val modes = resources.getStringArray(R.array.mode)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_setting_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: Entered Successfully")
        languageSpinner = view.findViewById(R.id.spinner_language)
        modeSpinner = view.findViewById(R.id.spinner_mode)


//        val langAdapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,languages)
//        languageSpinner.adapter = langAdapter
//
//
//        languageSpinner.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }
//
//        val modeAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,modes)
//        modeSpinner.adapter = modeAdapter
//
//
//        modeSpinner.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }

    }


}