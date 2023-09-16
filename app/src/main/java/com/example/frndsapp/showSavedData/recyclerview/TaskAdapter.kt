package com.example.frndsapp.showSavedData.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.frndsapp.R
import com.example.frndsapp.databinding.TaskItemBinding
import com.example.frndsapp.showSavedData.model.TaskItem

class TaskAdapter(
    private val tasks : List<TaskItem>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<TaskAdapter.TasksViewHolder>() {


    inner class TasksViewHolder(
        val recyclerviewTaskItemBinding: TaskItemBinding
    ) : RecyclerView.ViewHolder(recyclerviewTaskItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TasksViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.task_item,
                parent,
                false)
        )


    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.recyclerviewTaskItemBinding.task = tasks[position]
        holder.recyclerviewTaskItemBinding.deleteTask.setOnClickListener {
            if (it is Button) {
                if (it.text == "Deleted") {
                    Toast.makeText(it.context,"Already Deleted",Toast.LENGTH_LONG).show()
                } else {
                    listener.deleteCreatedTask(taskId = tasks[position].taskId,it)
                    it.text = "Deleted"
                }
            }
        }
        holder.recyclerviewTaskItemBinding.itemCard.setOnLongClickListener {
            holder.recyclerviewTaskItemBinding.deleteTask.visibility = View.VISIBLE
            true
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}