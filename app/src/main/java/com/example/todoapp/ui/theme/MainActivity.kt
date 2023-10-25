package com.example.todoapp.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.ui.theme.fragments.AddTodoBottomSheetFragment
import com.example.todoapp.ui.theme.fragments.ListFragment
import com.example.todoapp.ui.theme.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var addTodoButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews(){
        bottomNavigationView = findViewById(R.id.home_bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener {
            if(it.itemId == R.id.navigation_list){
                pushFragment(ListFragment())

            } else if(it.itemId == R.id.navigation_settings) {
                pushFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_list

        addTodoButton = findViewById(R.id.add_todo)
        addTodoButton.setOnClickListener {
            val addTodoBottomSheetFragment = AddTodoBottomSheetFragment()
            addTodoBottomSheetFragment.show(supportFragmentManager,null)


        }
    }

    fun pushFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()

    }
}
