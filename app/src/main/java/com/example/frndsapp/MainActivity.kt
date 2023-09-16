package com.example.frndsapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frndsapp.api.MainRepository
import com.example.frndsapp.api.RetrofitHelper
import com.example.frndsapp.api.TasksService
import com.example.frndsapp.databinding.ActivityMainBinding
import com.example.frndsapp.showSavedData.ShowSavedDataActivity
import com.example.frndsapp.viewModel.MainActivityViewModel
import com.example.frndsapp.viewModel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var _binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val taskService = RetrofitHelper.getInstance().create(TasksService::class.java)
        val repository = MainRepository(taskService)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainActivityViewModel::class.java]
        _binding.lifecycleOwner = this
        _binding.viewModel = viewModel
        setBindings()
        setClickListeners()
        setObserver()
    }

    private fun setObserver() {
        viewModel.toast.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })
    }

    private fun setBindings() {
        _binding.monthName.text = viewModel.getSelectedMonth()
        _binding.yearName.text = viewModel.getSelectedYear()
    }

    private fun setClickListeners() {
        _binding.monthName.setOnClickListener {
            showPopupMenu(it,R.menu.month_popup_menu,"month")
        }
        _binding.yearName.setOnClickListener {
            showPopupMenu(it,R.menu.year_popup_menu,"year")
        }
        _binding.monthDecreaser.setOnClickListener{
            viewModel.decreaseMonth()
            _binding.monthName.text = viewModel.getSelectedMonth()
            _binding.yearName.text = viewModel.getSelectedYear()
        }
        _binding.monthIncreaser.setOnClickListener{
            viewModel.increaseMonth()
            _binding.monthName.text = viewModel.getSelectedMonth()
            _binding.yearName.text = viewModel.getSelectedYear()
        }
        _binding.showAddedTask.setOnClickListener {
            val intent = Intent(this, ShowSavedDataActivity::class.java)
            intent.putExtra("userId", viewModel.getUserId())
            startActivity(intent)
        }
        _binding.btnAddTask.setOnClickListener {
            if(_binding.selectedDate.isVisible){
                showBottomDialog()
            }else{
                Toast.makeText(this,"Please, Choose a date first",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPopupMenu(view: View, popupMenu: Int, type: String) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(popupMenu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            if(type == "month"){
                (view as TextView).text = item.title.toString()
                viewModel.getMonthIndex(item.title.toString())?.let {
                     viewModel.changeCalenderData(it,_binding.yearName.text.toString().toInt())
                }
            }else{
                (view as TextView).text = item.title.toString()
                viewModel.getMonthIndex(_binding.monthName.text.toString())?.let {
                    viewModel.changeCalenderData(it,item.title.toString().toInt())
                }
            }
            true
        }

        popup.show()
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(R.layout.add_task_layout)
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.gravity = Gravity.BOTTOM
            window.attributes = layoutParams
        }
        dialog.show()
        val submitTaskButton = dialog.findViewById<Button>(R.id.btnAddTasks)
        val tasksTitleEditText = dialog.findViewById<AppCompatEditText>(R.id.tasksTitleEditText)
        val tasksDescriptionEditText = dialog.findViewById<AppCompatEditText>(R.id.tasksDescriptionEditText)
        submitTaskButton.setOnClickListener {
            if(tasksTitleEditText.text.toString() == "" || tasksDescriptionEditText.text.toString() == ""){
                Toast.makeText(this,"Please, Type Title and Description ",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.postTaskData(tasksTitleEditText.text.toString(),tasksDescriptionEditText.text.toString())
                dialog.dismiss()
            }
        }
    }
}