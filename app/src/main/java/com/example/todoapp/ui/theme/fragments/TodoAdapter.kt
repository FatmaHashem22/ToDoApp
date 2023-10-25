package com.example.todoapp.ui.theme.fragments

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.Update
import com.example.todoapp.R
import com.example.todoapp.database.model.Todo

class TodoAdapter(var todoList : List<Todo>?, val doneColor : Int, val primaryColor : Int) : Adapter<TodoAdapter.TodoViewHolder>() {

    var onDeleteClickListener : OnDeleteClickListener? = null
    var isDoneClickListener : IsDoneClickListener? = null
    var notDoneClickListener : IsDoneClickListener? = null
    var onItemClicked : OnItemClicked? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val viewBinding = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList?.get(position)
        holder.taskTitle.text = item?.todoName
        holder.taskDate.text = "  "+item?.todoDescription
        if (item?.isDone == true) {
            holder.line.setBackgroundColor(doneColor)
            holder.doneText.visibility = View.VISIBLE
            holder.checkImage.visibility = View.INVISIBLE
            holder.taskTitle.setTextColor(doneColor)
        }
        else {
            item?.isDone = false
            holder.line.setBackgroundColor(primaryColor)
            holder.checkImage.visibility = View.VISIBLE
            holder.doneText.visibility =View.INVISIBLE
            holder.taskTitle.setTextColor(primaryColor)
        }
        holder.checkImage.setOnClickListener {

            isDoneClickListener?.isDoneClick(item?:return@setOnClickListener,position,holder.taskTitle,holder.line,holder.doneText,holder.checkImage)

        }

        holder.doneText.setOnClickListener{
            notDoneClickListener?.isDoneClick(item?:return@setOnClickListener,position,holder.taskTitle,holder.line,holder.doneText,holder.checkImage)
        }

        holder.card.setOnClickListener{
            onItemClicked?.onItemClick(item?:return@setOnClickListener)
        }

        holder.deleteLayout.setOnClickListener{
            onDeleteClickListener?.onDeleteClick(item?:return@setOnClickListener, position)
        }
    }

    fun updateData(todoList: List<Todo>?) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return todoList?.size ?: 0
    }

    class TodoViewHolder(itemView : View) : ViewHolder(itemView) {
        val card : CardView = itemView.findViewById(R.id.drag_item)
        val taskTitle : TextView = itemView.findViewById(R.id.task_title)
        val taskDate : TextView = itemView.findViewById(R.id.date_text)
        val checkImage : ImageView = itemView.findViewById(R.id.check_task_icon)
        val doneText : TextView = itemView.findViewById(R.id.done_text)
        val line : View = itemView.findViewById(R.id.side_line)
        val deleteLayout : ImageView = itemView.findViewById(R.id.right_view)
    }


}

interface OnDeleteClickListener{
    fun onDeleteClick(todo : Todo, position: Int)
}

interface IsDoneClickListener{
    fun isDoneClick(todo: Todo,position: Int,title : TextView, line : View, doneTxt: TextView, doneIc : ImageView)
}

interface OnItemClicked{
    fun onItemClick(todo : Todo)
}