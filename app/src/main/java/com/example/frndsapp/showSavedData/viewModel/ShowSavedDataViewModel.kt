package com.example.frndsapp.showSavedData.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frndsapp.showSavedData.api.SavedDataRepository
import com.example.frndsapp.showSavedData.model.DeleteTaskBody
import com.example.frndsapp.showSavedData.model.GetTaskBody
import com.example.frndsapp.showSavedData.model.TaskItem
import com.example.frndsapp.showSavedData.model.Tasks
import com.example.frndsapp.showSavedData.recyclerview.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShowSavedDataViewModel(private val repository: SavedDataRepository) : ViewModel() {

    companion object {
        const val USERID = 123
    }
    private val _userTaskList = MutableLiveData<List<TaskItem>>()
    val userTaskList: LiveData<List<TaskItem>> = _userTaskList

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    init {
        getUserTaskList()
    }

    private fun getUserTaskList() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getTasks(GetTaskBody(USERID))
            if(result.isSuccessful){
                val tasks = result.body()?.tasks
                val sortedTasks = tasks?.sortedByDescending { taskItem ->
                    val createdDate = taskItem.taskDetail.createdDate?.let { parseDate(it) }
                    createdDate
                }
                sortedTasks?.let {
                    _userTaskList.postValue(it)
                }

            }else{
                _toast.postValue("Something Went Wrong")
            }
        }
    }

    fun deleteCreatedTask(taskId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.deleteTask(DeleteTaskBody(USERID,taskId))
            if(result.isSuccessful){
                _toast.postValue("Task Deleted Successfully")
            }else{
                _toast.postValue("Something Went Wrong")
            }
        }
    }
    private fun parseDate(dateString: String): Date? {
        try {
            val dateFormat = SimpleDateFormat("dd, MM, yyyy HH:mm:ss", Locale.US)
            return dateFormat.parse(dateString)
        }catch (e : Exception){
            e.message
        }
        return null
    }
}