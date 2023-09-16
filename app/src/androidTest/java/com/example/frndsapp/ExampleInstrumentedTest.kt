package com.example.frndsapp

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.frndsapp.api.RetrofitHelper
import com.example.frndsapp.showSavedData.api.SavedDataRepository
import com.example.frndsapp.showSavedData.api.SavedDataService
import com.example.frndsapp.showSavedData.viewModel.ShowSavedDataViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var viewModel: ShowSavedDataViewModel

    @Before
    fun setup() {
        val savedService = RetrofitHelper.getInstance().create(SavedDataService::class.java)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repository = SavedDataRepository(savedService)
        viewModel = ShowSavedDataViewModel(repository)
    }

    @Test
    fun testToastObservation() {
        val toastLiveData = MutableLiveData<String>()
        viewModel.toast.observeForever { toastLiveData.value = it }
        Thread.sleep(100)
        Espresso.onView(ViewMatchers.withText("Hello, Toast!"))
            .inRoot(RootMatchers.DEFAULT)
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}