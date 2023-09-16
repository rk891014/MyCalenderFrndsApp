package com.example.frndsapp.viewModel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frndsapp.api.MainRepository
import com.example.frndsapp.model.PostTaskBody
import com.example.frndsapp.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivityViewModel(private val repository: MainRepository) : ViewModel() {

    private val calendarData = List(7) { MutableList(7) { "" } }
    private val _myLiveData = MutableLiveData<List<MutableList<String>>>()
    val myLiveData: LiveData<List<MutableList<String>>> = _myLiveData

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> = _toast

    private var selectedMonth : String = ""
    private var selectedYear : String = ""

    companion object {
        const val USERID = 123
    }

    init {
        getCurrentDateMonthYear()
    }

    private fun updateData(calendarData: List<MutableList<String>>) {
        _myLiveData.value = calendarData
    }

    private fun getCurrentDateMonthYear() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        _selectedDate.value = getCurrentDate().substring(0,2)
        setCalenderData(month = currentMonth, year = currentYear)
    }
    fun changeCalenderData( calenderMonth : Int, calenderYear : Int) {
        for (i in calendarData.indices) {
            for (j in calendarData[i].indices) {
                calendarData[i][j] = ""
            }
        }
        _selectedDate.value = null
        setCalenderData(month = calenderMonth, year = calenderYear)
    }
    private fun setCalenderData(day : Int = 1, month : Int, year : Int){

        selectedMonth = getMonthName(month).toString()
        selectedYear = year.toString()
        val firstDayIndex = dayOfWeek(day,month,year) - 1
        val monthLastDay = getLastDayOfMonth(year,month)
        setDaysName()
        var j = firstDayIndex
        var currentDate = 1
        for(i in 1 until 7){
            while(j < 7 && currentDate <= monthLastDay){
                calendarData[i][j] = currentDate.toString()
                currentDate++
                j++
            }
            j = 0
        }
        updateData(calendarData)
    }

    private fun setDaysName() {
        calendarData[0][0] = "Su"
        calendarData[0][1] = "Mo"
        calendarData[0][2] = "Tu"
        calendarData[0][3] = "We"
        calendarData[0][4] = "Th"
        calendarData[0][5] = "Fr"
        calendarData[0][6] = "Sa"
    }

    fun getMonthName(month: Int): String? {
        val hashMap = hashMapOf(1 to "January", 2 to "February", 3 to  "March", 4 to  "April", 5 to "May", 6 to "June"
            , 7 to "July", 8 to "August", 9 to "September", 10 to "October", 11 to "November", 12 to "December")
        return hashMap[month]
    }
    fun dayOfWeek(day: Int, month: Int, year: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }
    public fun getLastDayOfMonth(year: Int, month: Int): Int {
        if (month < 1 || month > 12) {
            throw IllegalArgumentException("Invalid month: $month")
        }
        if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)) {
            return 29
        }
        val daysInMonth = intArrayOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        return daysInMonth[month]
    }

    fun getMonthIndex(month : String) : Int? {
        val hashMap = hashMapOf("January" to 1, "February" to 2, "March" to 3, "April" to 4, "May" to 5, "June" to 6
            , "July" to 7, "August" to 8, "September" to 9, "October" to 10, "November" to 11, "December" to 12)
        return hashMap[month]
    }

    fun onDateSelected(view : View){
        if(view is TextView && view.text.toString() != ""){
            _selectedDate.value = view.text.toString()
        }
    }

    fun getSelectedMonth() : String{
        return selectedMonth
    }

    fun getSelectedYear() : String{
        return selectedYear
    }

    fun postTaskData(taskTitle: String, taskDescription: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.setTasks(PostTaskBody(
                USERID, Task(taskTitle,taskDescription,getCurrentDate(),
                selectedDate.value.toString()+", "+selectedMonth+", "+selectedYear)))
            if(result.isSuccessful){
                _toast.postValue("Updated Successfully")
            }else{
                _toast.postValue("Something Went Wrong")
            }
        }
    }
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd, MM, yyyy HH:mm:ss", Locale.US)
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        return dateFormat.format(currentDate)
    }

    fun increaseMonth() {
        val month = getSelectedMonth()
        if(month == "December"){
            changeCalenderData(1,selectedYear.toInt() + 1)
        }else{
            changeCalenderData(getMonthIndex(selectedMonth)?.plus(1) ?: 1,selectedYear.toInt())
        }
    }

    fun decreaseMonth() {
        val month = getSelectedMonth()
        if(month == "January"){
            changeCalenderData(12,selectedYear.toInt() - 1)
        }else{
            changeCalenderData(getMonthIndex(selectedMonth)?.minus(1) ?: 1,selectedYear.toInt())
        }
    }

    fun getUserId(): Int {
        return USERID
    }
}