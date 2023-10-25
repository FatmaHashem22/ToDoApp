package com.example.todoapp.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.database.Converters
import com.example.todoapp.ui.theme.Constants.Companion.TODO
import com.example.todoapp.database.model.Todo
import com.example.todoapp.databinding.EditFragmentBinding
import com.example.todoapp.ui.theme.ui.theme.ToDoAppTheme
import java.text.SimpleDateFormat
import java.util.Date

class EditActivity : AppCompatActivity() {

    private lateinit var binding : EditFragmentBinding


    lateinit var todo : Todo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EditFragmentBinding.inflate(layoutInflater)

        setContentView(binding.root)



        todo = ((intent.getSerializableExtra("todo") as? Todo)!!)

        showData(todo)

        binding.submit.setOnClickListener {  }


    }

    private fun showData(todo: Todo) {
        binding.titleContainer.editText?.setText(todo.todoName)
        binding.editDate.text = todo.date.toString()
        binding.descriptionContainer.editText?.setText(todo.todoDescription)
    }



}

