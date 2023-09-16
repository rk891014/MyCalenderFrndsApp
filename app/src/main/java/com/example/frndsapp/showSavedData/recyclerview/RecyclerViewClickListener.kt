package com.example.frndsapp.showSavedData.recyclerview

import android.widget.Button

interface RecyclerViewClickListener {
    fun deleteCreatedTask(taskId: Int, view: Button)
}