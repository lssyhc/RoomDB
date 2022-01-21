package com.example.roomdb_cahyo_06

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb_cahyo_06.databinding.ListHomeworkBinding
import com.example.roomdb_cahyo_06.room.Homework

class HomeworkAdapter(private val homeworks : ArrayList<Homework>, private val listener : OnAdapterChangeListener) : RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>() {
    class HomeworkViewHolder(val binding: ListHomeworkBinding): RecyclerView.ViewHolder(binding.root)
    interface OnAdapterChangeListener {
        fun onRead(homework: Homework)
        fun onUpdate(homework: Homework)
        fun onDelete(homework: Homework)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkViewHolder {
        return HomeworkViewHolder(
            ListHomeworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        val homework = homeworks[position]
        holder.binding.textTitle.text = homework.title
        holder.binding.textTitle.setOnClickListener {
            listener.onRead(homework)
        }
        holder.binding.iconEdit.setOnClickListener {
            listener.onUpdate(homework)
        }
        holder.binding.iconDelete.setOnClickListener {
            listener.onDelete(homework)
        }
    }

    override fun getItemCount() = homeworks.size

    fun setData(list: List<Homework>){
        homeworks.clear()
        homeworks.addAll(list)
        notifyDataSetChanged()
    }
}