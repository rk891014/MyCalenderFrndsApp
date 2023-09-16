package com.example.frndsapp.showSavedData.api

import com.example.frndsapp.showSavedData.model.DeleteTaskBody
import com.example.frndsapp.showSavedData.model.GetTaskBody
import com.example.frndsapp.showSavedData.model.Tasks
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SavedDataService {

    @POST("api/getCalendarTaskList")
    suspend fun getTasks(
        @Body userId : GetTaskBody
    ) : Response<Tasks>


    @POST("api/deleteCalendarTask")
    suspend fun deleteTask(
        @Body body : DeleteTaskBody
    ) : Response<*>

}