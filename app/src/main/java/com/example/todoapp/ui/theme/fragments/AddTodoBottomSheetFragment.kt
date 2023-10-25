package com.example.todoapp.ui.theme.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.todoapp.R
import com.example.todoapp.database.MyDatabase
import com.example.todoapp.database.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTodoBottomSheetFragment : BottomSheetDialogFragment() {


    lateinit var titleEditText: EditText
    lateinit var detailsEditText : EditText
    lateinit var selectDate : TextView
    lateinit var addTodoButton : Button
    lateinit var calendar : Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_todo_fragment,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }


    fun initViews(view : View) {
        titleEditText = view.findViewById(R.id.input_todo_title)
        detailsEditText = view.findViewById(R.id.input_todo_description)
        calendar = Calendar.getInstance()
        selectDate = view.findViewById(R.id.task_date)
        selectDate.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext())
            datePicker.show()
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                calendar.set(Calendar.MONTH,month)
                selectDate.text = "$dayOfMonth/${month+1}/$year"
            }
        }
        addTodoButton = view.findViewById(R.id.add_new_task_btn)
        addTodoButton.setOnClickListener {
            if (validateForm()) {
                calendar.clearTime()
                MyDatabase
                    .getInstance(context = requireContext())
                    .getTodoDao()
                    .insertTodo(Todo(
                        todoName = titleEditText.text.toString(),
                        todoDescription = detailsEditText.text.toString(),
                        date = calendar.time
                    ))
            }
            dismiss()


        }
    }

    fun validateForm() : Boolean {
        var isValid = true
        if(titleEditText.text.isEmpty()){
            isValid = false
            titleEditText.error = "Title shouldn't be empty"
        } else if (detailsEditText.text.isEmpty()) {
            isValid = false
            detailsEditText.error = "Description shouldn't be empty"
        } else if (selectDate.text == "Select date"){
            isValid = false
        }
        return isValid
    }
}