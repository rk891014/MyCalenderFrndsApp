package com.example.frndsapp.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("created_date")
    val createdDate : String,
    @SerializedName("task_date")
    val taskDate : String
)

data class PostTaskBody(
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("task")
    val task: Task
)

