package com.example.roomdb_cahyo_06

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb_cahyo_06.databinding.ListTodolistBinding
import com.example.roomdb_cahyo_06.room.Todolist

class TodolistAdapter(private val todolists : ArrayList<Todolist>, private val listener : OnAdapterChangeListener) : RecyclerView.Adapter<TodolistAdapter.TodolistViewHolder>() {
    class TodolistViewHolder(val binding: ListTodolistBinding): RecyclerView.ViewHolder(binding.root)
    interface OnAdapterChangeListener {
        fun onRead(todolist: Todolist)
        fun onUpdate(todolist: Todolist)
        fun onDelete(todolist: Todolist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodolistViewHolder {
        return TodolistViewHolder(
            ListTodolistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodolistViewHolder, position: Int) {
        val todolist = todolists[position]
        holder.binding.textTitle.text = todolist.title
        holder.binding.textTitle.setOnClickListener {
            listener.onRead(todolist)
        }
        holder.binding.iconEdit.setOnClickListener {
            listener.onUpdate(todolist)
        }
        holder.binding.iconDelete.setOnClickListener {
            listener.onDelete(todolist)
        }
    }

    override fun getItemCount() = todolists.size

    fun setData(list: List<Todolist>){
        todolists.clear()
        todolists.addAll(list)
        notifyDataSetChanged()
    }
}