package com.example.frndsapp.api

import android.util.Log
import com.example.frndsapp.model.PostTaskBody
import retrofit2.Response

class MainRepository(private val tasksService: TasksService) {

    suspend fun setTasks(task : PostTaskBody) : Response<*> {
        return tasksService.setTasks(task)
    }
}