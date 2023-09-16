package com.example.frndsapp.showSavedData.model

import com.google.gson.annotations.SerializedName

data class Tasks(
    @SerializedName("tasks")
    val tasks: List<TaskItem>
)

data class TaskItem(
    @SerializedName("task_id")
    val taskId: Int,
    @SerializedName("task_detail")
    val taskDetail: TaskDetail
)

data class TaskDetail(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("task_date")
    val taskDate: String?,
    @SerializedName("created_date")
    val createdDate: String?
)

data class GetTaskBody(
    @SerializedName("user_id")
    val userId : Int
)

data class DeleteTaskBody(
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("task_id")
    val taskId : Int
)