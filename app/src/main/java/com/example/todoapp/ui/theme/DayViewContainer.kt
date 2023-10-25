package com.example.todoapp.ui.theme

import android.view.View
import android.widget.TextView
import com.example.todoapp.R
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate

class DayViewContainer(view : View) : ViewContainer(view) {

    val dayOfWeek : TextView = view.findViewById(R.id.calendar_day_text)
    val dayTextView : TextView = view.findViewById(R.id.calendar_day_date)

}

