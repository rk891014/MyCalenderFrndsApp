package com.example.frndsapp

import com.example.frndsapp.api.MainRepository
import com.example.frndsapp.api.RetrofitHelper
import com.example.frndsapp.api.TasksService
import com.example.frndsapp.viewModel.MainActivityViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var repository : MainRepository

    @Before
    fun setup() {
        val taskService = RetrofitHelper.getInstance().create(TasksService::class.java)
        repository = MainRepository(taskService)
        viewModel = MainActivityViewModel(repository)
    }

    @Test
    fun testGetMonthName() {
        assertEquals("January", viewModel.getMonthName(1))
        assertEquals("February", viewModel.getMonthName(2))
        assertEquals("March", viewModel.getMonthName(3))
        assertEquals("April", viewModel.getMonthName(4))
        assertEquals("May", viewModel.getMonthName(5))
        assertEquals("June", viewModel.getMonthName(6))
        assertEquals("July", viewModel.getMonthName(7))
        assertEquals("August", viewModel.getMonthName(8))
        assertEquals("September", viewModel.getMonthName(9))
        assertEquals("October", viewModel.getMonthName(10))
        assertEquals("November", viewModel.getMonthName(11))
        assertEquals("December", viewModel.getMonthName(12))

        assertEquals(null, viewModel.getMonthName(0))
        assertEquals(null, viewModel.getMonthName(13))
        assertEquals("January", viewModel.getMonthName(1))
        assertEquals("February", viewModel.getMonthName(2))
    }

    @Test
    fun testGetLastDayOfMonth() {
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 1))
        assertEquals(28, viewModel.getLastDayOfMonth(2022, 2))
        assertEquals(29, viewModel.getLastDayOfMonth(2024, 2))
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 3))
        assertEquals(30, viewModel.getLastDayOfMonth(2023, 4))
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 5))
        assertEquals(30, viewModel.getLastDayOfMonth(2023, 6))
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 7))
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 8))
        assertEquals(30, viewModel.getLastDayOfMonth(2023, 9))
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 10))
        assertEquals(30, viewModel.getLastDayOfMonth(2023, 11))
        assertEquals(31, viewModel.getLastDayOfMonth(2023, 12))

        try {
            viewModel.getLastDayOfMonth(2023, 0)
        } catch (e: IllegalArgumentException) {
            assertEquals("Invalid month: 0", e.message)
        }
    }

    @Test
    fun testDayOfWeek() {
        assertEquals(1, viewModel.dayOfWeek(1, 1, 2023))
        assertEquals(4, viewModel.dayOfWeek(15, 2, 2023))
        assertEquals(1, viewModel.dayOfWeek(31, 12, 2023))
        assertEquals(-1, viewModel.dayOfWeek(31, 4, 2023))
    }
    @Test
    fun testIncreaseMonth() {
        assertEquals("September", viewModel.getSelectedMonth())
        assertEquals("2023", viewModel.getSelectedYear())

        viewModel.increaseMonth()

        assertEquals("October", viewModel.getSelectedMonth())
        assertEquals("2023", viewModel.getSelectedYear())

        viewModel.increaseMonth()

        assertEquals("November", viewModel.getSelectedMonth())
        assertEquals("2023", viewModel.getSelectedYear())

        viewModel.increaseMonth()

        assertEquals("December", viewModel.getSelectedMonth())
        assertEquals("2024", viewModel.getSelectedYear())

        viewModel.increaseMonth()

        assertEquals("January", viewModel.getSelectedMonth())
        assertEquals("2023", viewModel.getSelectedYear())
    }


    @Test
    fun testDecreaseMonth() {
        viewModel.changeCalenderData(1, 2023)
        viewModel.decreaseMonth()
        assertEquals("December", viewModel.getSelectedMonth())
        assertEquals("2022", viewModel.getSelectedYear())

        viewModel.changeCalenderData(4, 2023)
        viewModel.decreaseMonth()
        assertEquals("March", viewModel.getSelectedMonth())
        assertEquals("2023", viewModel.getSelectedYear())

        viewModel.changeCalenderData(12, 2023)
        viewModel.decreaseMonth()
        assertEquals("November", viewModel.getSelectedMonth())
        assertEquals("2023", viewModel.getSelectedYear())
    }
}