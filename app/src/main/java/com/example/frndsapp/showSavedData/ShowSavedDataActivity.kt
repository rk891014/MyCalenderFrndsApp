package com.example.frndsapp.showSavedData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frndsapp.R
import com.example.frndsapp.api.RetrofitHelper
import com.example.frndsapp.databinding.ActivityShowSavedDataBinding
import com.example.frndsapp.showSavedData.api.SavedDataRepository
import com.example.frndsapp.showSavedData.api.SavedDataService
import com.example.frndsapp.showSavedData.recyclerview.RecyclerViewClickListener
import com.example.frndsapp.showSavedData.recyclerview.TaskAdapter
import com.example.frndsapp.showSavedData.viewModel.ShowSavedDataViewModel
import com.example.frndsapp.showSavedData.viewModel.ShowSavedDataViewModelFactory

class ShowSavedDataActivity : AppCompatActivity(), RecyclerViewClickListener {

    private lateinit var viewModel: ShowSavedDataViewModel
    private lateinit var _binding : ActivityShowSavedDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityShowSavedDataBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        val savedService = RetrofitHelper.getInstance().create(SavedDataService::class.java)
        val repository = SavedDataRepository(savedService)
        viewModel = ViewModelProvider(this, ShowSavedDataViewModelFactory(repository))[ShowSavedDataViewModel::class.java]
        _binding.lifecycleOwner = this
        _binding.viewModel = viewModel
        setObservers()
        setToolbar()

    }

    private fun setObservers() {

        viewModel.toast.observe(this, Observer {
            Toast.makeText(this,it, Toast.LENGTH_LONG).show()
        })

        viewModel.userTaskList.observe(this, Observer {tasks->
            _binding.taskList.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = TaskAdapter(tasks,this)
            }
        })
    }

    override fun deleteCreatedTask(taskId: Int, view: Button) {
        val textColor = ContextCompat.getColor(this, R.color.red)
        view.setTextColor(textColor)
        viewModel.deleteCreatedTask(taskId)
    }
    private fun setToolbar() {
        setSupportActionBar(_binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}