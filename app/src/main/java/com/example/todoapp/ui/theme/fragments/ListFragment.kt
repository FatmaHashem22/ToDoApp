package com.example.todoapp.ui.theme.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.MyDatabase
import com.example.todoapp.database.model.Todo
import com.example.todoapp.ui.theme.Constants
import com.example.todoapp.ui.theme.Constants.Companion.TODO
import com.example.todoapp.ui.theme.DayViewContainer
import com.example.todoapp.ui.theme.EditActivity
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekCalendarView
import com.kizitonwose.calendar.view.WeekDayBinder
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

class ListFragment : BaseFragment() {

    lateinit var calendarView : WeekCalendarView
    lateinit var todoRecycler: RecyclerView
    lateinit var adapter : TodoAdapter
    var selectedDate : LocalDate? = null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_list_fragment,container,false)
    }

    override fun onResume() {
        super.onResume()
        val todos = MyDatabase
            .getInstance(requireContext())
            .getTodoDao().getTodos()
        adapter.updateData(todos)
    }

    var calendar : Calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoRecycler = view.findViewById(R.id.todos_recycler_view)
        adapter = TodoAdapter(null,
            ResourcesCompat.getColor(resources, R.color.green ,null),
            ResourcesCompat.getColor(resources, R.color.ColorPrimaryBlue ,null)
            )
        todoRecycler.adapter = adapter


        adapter.onDeleteClickListener = object : OnDeleteClickListener {
            override fun onDeleteClick(todo: Todo, position : Int) {
                MyDatabase
                    .getInstance(requireContext())
                    .getTodoDao()
                    .deleteTodo(todo)
                val todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodos()
                adapter.updateData(todos)
                adapter.notifyItemRemoved(position)

            }

        }

        adapter.onItemClicked = object : OnItemClicked {
            override fun onItemClick(todo: Todo) {
                showMessage("Would you like to edit this task?",
                    "YES",
                    { _, i -> updateTodo(todo) },
                    "NO",
                    { _, i -> makeTodoDone(todo) }
                )
            }
        }




        checkDone()


        calendarView = view.findViewById(R.id.calendar_view)
        calendarView.dayBinder = object : WeekDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)

            override fun bind(container: DayViewContainer, data: WeekDay) {

                container.dayTextView.text = data.date.dayOfMonth.toString()
                container.view.setOnClickListener{
                    val currentSelection = selectedDate
                    if (currentSelection == data.date) {
                        Log.e("TAG", "Date UnSelected ! ")
                        // If the user clicks the same date, clear selection.
                        selectedDate = null
                        val todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodos()
                        calendarView.notifyDateChanged(currentSelection)
                        adapter.updateData(todos)
                    } else {
                        Log.e("TAG", "Date Selected ! ")
                        selectedDate = data.date
                        // Reload the newly selected date so the dayBinder is
                        // called and we can ADD the selection background.
                        calendarView.notifyDateChanged(data.date)
                        if (currentSelection != null) {
                            // We need to also reload the previously selected
                            // date so we can REMOVE the selection background.
                            calendarView.notifyDateChanged(currentSelection)
                        }
                    }
                }
                container.dayOfWeek.text = data.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault()
                )
                if(data.date == selectedDate) {
                    container.dayTextView
                        .setTextColor(
                            ResourcesCompat
                                .getColor(
                                    resources,
                                    R.color.ColorPrimaryBlue,
                                    null))
                    container.dayOfWeek
                        .setTextColor(
                            ResourcesCompat
                                .getColor(
                                    resources,
                                    R.color.ColorPrimaryBlue,
                                    null))

                    Log.e("TAG","bind : ${calendar.get(Calendar.MONTH)}")
                    Log.e("TAG","bind : ${data.date.monthValue}")

                    val month = data.date.monthValue - 1
                    calendar.set(Calendar.YEAR,data.date.year)
                    calendar.set(Calendar.MONTH,month)
                    calendar.set(Calendar.DAY_OF_MONTH,data.date.dayOfMonth)
                    calendar.clearTime()



                    val todos = MyDatabase
                        .getInstance(
                            requireContext())
                        .getTodoDao()
                        .getTodoByDate(calendar.time)
                    adapter.updateData(todos)
                }
                else {
                    container.dayTextView.setTextColor(ResourcesCompat.getColor(resources,R.color.black,null))
                    container.dayOfWeek.setTextColor(ResourcesCompat.getColor(resources,R.color.black,null))
                }
            }
        }


        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(100).atStartOfMonth()  // Adjust as needed
        val endDate = currentMonth.plusMonths(100).atEndOfMonth() // Adjust as needed
        val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
        firstDayOfWeek.name.substring(0,3)
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SATURDAY)
        calendarView.setup(startDate, endDate , daysOfWeek.first())
        calendarView.scrollToWeek(currentDate)

    }


    private fun updateTodo(todo: Todo) {
        var intent = Intent(requireContext(), EditActivity::class.java)
        intent.putExtra(TODO, todo)
        startActivity(intent)
    }

    private fun makeTodoDone(todo: Todo){}



    fun checkDone() {
        adapter.isDoneClickListener = object : IsDoneClickListener {


            override fun isDoneClick(
                todo: Todo,
                position : Int,
                title: TextView,
                line: View,
                doneTxt: TextView,
                doneIc: ImageView
            ) {
                todo.isDone = true
                title.setTextColor(ResourcesCompat.getColor(resources,R.color.green,null))
                line.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.green,null))
                doneIc.visibility = View.INVISIBLE
                doneTxt.setTextColor(ResourcesCompat.getColor(resources,R.color.green,null))
                MyDatabase
                    .getInstance(requireContext())
                    .getTodoDao()
                    .updateTodo(todo)
                if(selectedDate != null){
                    val todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodos()
                    calendarView.notifyDateChanged(selectedDate!!)
                    adapter.updateData(todos)
                } else onResume()




            }

        }

        adapter.notDoneClickListener = object : IsDoneClickListener {


            override fun isDoneClick(
                todo: Todo,
                position : Int,
                title: TextView,
                line: View,
                doneTxt: TextView,
                doneIc: ImageView
            ) {
                todo.isDone = false
                title.setTextColor(ResourcesCompat.getColor(resources,R.color.ColorPrimaryBlue,null))
                line.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.ColorPrimaryBlue,null))
                doneIc.visibility = View.VISIBLE
                doneTxt.visibility = View.INVISIBLE

                MyDatabase
                    .getInstance(requireContext())
                    .getTodoDao()
                    .updateTodo(todo)
                if(selectedDate != null){
                    val todos = MyDatabase.getInstance(requireContext()).getTodoDao().getTodos()
                    calendarView.notifyDateChanged(selectedDate!!)
                    adapter.updateData(todos)
                } else onResume()

            }

        }
    }
}

