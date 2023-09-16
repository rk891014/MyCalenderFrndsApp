package com.example.frndsapp.api
import com.example.frndsapp.model.PostTaskBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TasksService {

    @POST("api/storeCalendarTask")
    suspend fun setTasks(
        @Body task : PostTaskBody
    ) : Response<*>

}