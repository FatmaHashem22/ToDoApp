package com.example.todoapp.ui.theme

import android.content.Intent
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
import com.example.todoapp.database.MyDatabase
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

        binding.submit.setOnClickListener {

            updateEdiedTodo()
        }


    }

    private fun isDateValid(): Boolean {
        var isValid = true

        if (binding.titleContainer.editText?.text.toString().isBlank()) {
            binding.titleContainer.error = "Please Enter title"
            isValid = false
        } else
            binding.titleContainer.error = null

        if (binding.descriptionContainer.editText?.text.toString().isBlank()) {
            binding.descriptionContainer.error = "Please Enter description"
            isValid = false
        } else
            binding.dateContainer.error = null

        if(binding.editDate.text.isBlank()) {
            binding.editDate.error = "Please Enter Date"
            isValid = false
        } else
            binding.editDate.error = null

        return isValid
    }

    private fun updateEdiedTodo() {
        if(isDateValid()) {
            todo.todoName = binding.titleContainer.editText?.text.toString()
            todo.todoDescription = "  "+binding.descriptionContainer.editText?.text.toString()
//            val date = binding.editDate.text.toString()
//

            MyDatabase.getInstance(this).getTodoDao().updateTodo(todo)
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }

    private fun showData(todo: Todo) {
        binding.titleContainer.editText?.setText(todo.todoName)
        val date = SimpleDateFormat("EEEE dd-MM-yyyy , HH:MM ")
        binding.editDate.text = date.format(todo.date).toString()
        binding.descriptionContainer.editText?.setText("  "+todo.todoDescription)
    }



}

