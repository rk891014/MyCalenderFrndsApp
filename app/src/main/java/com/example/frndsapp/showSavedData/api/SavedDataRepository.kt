package com.example.frndsapp.showSavedData.api

import com.example.frndsapp.showSavedData.model.DeleteTaskBody
import com.example.frndsapp.showSavedData.model.GetTaskBody
import com.example.frndsapp.showSavedData.model.Tasks
import retrofit2.Response

class SavedDataRepository (private val savedDataService: SavedDataService) {

    suspend fun getTasks(userId : GetTaskBody) : Response<Tasks> {
        return savedDataService.getTasks(userId)
    }

    suspend fun deleteTask(deleteTaskBody: DeleteTaskBody): Response<*> {
        return savedDataService.deleteTask(deleteTaskBody)
    }
}