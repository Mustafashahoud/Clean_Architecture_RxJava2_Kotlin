package com.android.cleanarch.bitcoinapp.features.bitcoinchart

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity
import com.android.cleanarch.bitcoinapp.*
import com.android.cleanarch.bitcoinapp.BitcoinChartUtils.makeBitcoinChartEntityView
import com.android.cleanarch.bitcoinapp.base.DataBindingIdlingResourceRule
import com.android.cleanarch.bitcoinapp.base.TaskExecutorWithIdlingResourceRule
import com.android.cleanarch.bitcoinapp.base.ViewModelUtil
import com.android.cleanarch.bitcoinapp.base.disableProgressBarAnimations
import com.android.cleanarch.bitcoinapp.common.Resource
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui.BitcoinPriceChartFragment
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui.BitcoinPriceChartViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BitcoinPriceChartFragmentTest {


    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    private lateinit var viewModel: BitcoinPriceChartViewModel
    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )
    private val results = MutableLiveData<Resource<BitcoinChartViewEntity>>()

    @Before
    fun init() {
        viewModel = mock()
        whenever(viewModel.bitcoinChartLiveData).thenReturn(results)

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            BitcoinPriceChartFragment().apply {
                viewModelFactory = ViewModelUtil.createFor(viewModel)
            }
        }

        dataBindingIdlingResourceRule.monitorFragment(scenario)

        runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
        }

        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
            fragment.disableProgressBarAnimations()
        }
    }

    @Test
    fun testBasics_ProgressBar() {
        verifyProgressbarNotDisplayed()

        results.postValue(Resource.Loading())

        verifyProgressbarDisplayed()
    }

    @Test
    fun testSuccessResult_ChartVisible_BitcoinPriceVisible_ChartDescriptionVisible_ErrorNotVisible() {

        val bitcoinCurrentPrice = "30,235.89"
        val bitcoinChartViewEntity = makeBitcoinChartEntityView(currentPrice = bitcoinCurrentPrice)

        results.postValue(Resource.Success(bitcoinChartViewEntity))

        // Chart Visible
        verifyBitcoinChartIsDisplayed()

        // Bitcoin current price displayed with Text
        verifyViewDisplayedWithText(R.id.current_price_text_view, bitcoinCurrentPrice)

        // Bitcoin chart description displayed with Text
        verifyViewDisplayedWithText(
            R.id.market_price_description_text_view,
            bitcoinChartViewEntity.description
        )

        // Error related stuff not visible
        verifyErrorRelatedStuffNotVisible()

    }

    @Test
    fun testTimeSpanGroupButtons_Press_3Months_1Year_AllTime_ViewModelNotifyShouldBeCalled() {

        val bitcoinChartViewEntity = makeBitcoinChartEntityView()

        results.postValue(Resource.Success(bitcoinChartViewEntity))

        // Chart Visible
        verifyBitcoinChartIsDisplayed()

        // Verify 30 Days button is checked by default
        verifyButtonIsChecked(R.id.thirty_days_button)

        // Verify one_year_button year is not checked, perform a click on it, verify it is checked and then verify viewModel notifyMethod is called
        verifyButtonNotChecked(R.id.one_year_button)
        performClickOnView(R.id.one_year_button)
        verifyButtonIsChecked(R.id.one_year_button)
        verify(viewModel).notifyTimeSpanChanged("1year")


        verifyButtonNotChecked(R.id.three_months_button)
        performClickOnView(R.id.three_months_button)
        verifyButtonIsChecked(R.id.three_months_button)
        verify(viewModel).notifyTimeSpanChanged("3months")


        verifyButtonNotChecked(R.id.all_time_button)
        performClickOnView(R.id.all_time_button)
        verifyButtonIsChecked(R.id.all_time_button)
        verify(viewModel).notifyTimeSpanChanged("all")

    }

    @Test
    fun testNetworkErrorResult_RetryButton_IsVisible_GoBackButton_NotVisible() {

        verifyRetryNotDisplayed()

        // Post Network Error
        results.postValue(Resource.Error(ErrorEntity.Network))

        // Chart view Not visible
        verifyBitcoinChartNotDisplayed()

        // Retry is visible
        // Retry is only visible in case Network error E.g. "No connection"
        verifyRetryDisplayed()

        // Error Network text visible
        verifyTextViewsErrorAreVisibleWithText(getStringResourceById(R.string.network_error_message))

        // Go back is not visible
        // Go Back Button is only visible in case Server or Unknown error
        verifyGoBackNotDisplayed()
    }

    @Test
    fun testServerErrorResult_RetryButton_NotVisible_GoBackButton_IsVisible() {

        // Retry not visible
        verifyRetryNotDisplayed()

        // Go back not visible
        verifyGoBackNotDisplayed()

        // Post Server Error
        results.postValue(Resource.Error(ErrorEntity.Server))

        // Chart view Not visible
        verifyBitcoinChartNotDisplayed()

        // Error Server text visible
        verifyTextViewsErrorAreVisibleWithText(getStringResourceById(R.string.server_error_message))

        // Go back is visible
        verifyGoBackDisplayed()

        // Retry not visible
        verifyRetryNotDisplayed()

    }

    @Test
    fun testUnknownErrorResult_RetryButton_NotVisible_GoBackButton_IsVisible() {

        // Retry not visible
        verifyRetryNotDisplayed()

        // Go back not visible
        verifyGoBackNotDisplayed()

        // Post Server Error
        results.postValue(Resource.Error(ErrorEntity.Unknown))

        // Chart view Not visible
        verifyBitcoinChartNotDisplayed()

        // Error Unknown text visible
        verifyTextViewsErrorAreVisibleWithText(getStringResourceById(R.string.unknown_error_message))

        // Go back is visible
        verifyGoBackDisplayed()

        // Retry not visible
        verifyRetryNotDisplayed()

    }

    @Test
    fun testNetworkErrorResult_Press_Retry_ViewModelRetryCalled() {

        val bitcoinChartViewEntity = makeBitcoinChartEntityView()

        // Post Network Error
        results.postValue(Resource.Error(ErrorEntity.Network))

        //ChartNotDisplayed
        verifyBitcoinChartNotDisplayed()

        // Retry is visible
        verifyRetryDisplayed()

        performClickOnView(R.id.retry_text_view)

        verify(viewModel).retry()

        results.postValue(Resource.Success(bitcoinChartViewEntity))

        //ChartDisplayed
        verifyBitcoinChartIsDisplayed()

        // Retry is invisible
        verifyRetryNotDisplayed()
    }


    private fun verifyTextViewsErrorAreVisibleWithText(errorText: String) {
        onView(withId(R.id.error_message_title_text_view))
            .check(matches(isDisplayed()))

        onView(withId(R.id.error_message_text_view))
            .check(matches(isDisplayed()))

        verifyViewDisplayedWithText(R.id.error_message_text_view, errorText)

    }

    private fun verifyErrorRelatedStuffNotVisible() {
        onView(withId(R.id.error_message_title_text_view))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.error_message_text_view))
            .check(matches(not(isDisplayed())))

        verifyRetryNotDisplayed()

        verifyGoBackNotDisplayed()
    }

    private fun verifyViewDisplayedWithText(viewId: Int, textToVerify: String) {
        onView(withId(viewId))
            .check(matches(withText(textToVerify)))
    }


    private fun verifyProgressbarNotDisplayed() {
        onView(withId(R.id.market_price_chart_progressbar))
            .check(matches(not(isDisplayed())))
    }

    private fun verifyProgressbarDisplayed() {
        onView(withId(R.id.market_price_chart_progressbar))
            .check(matches(isDisplayed()))
    }

    private fun verifyRetryNotDisplayed() {
        onView(withId(R.id.retry_text_view))
            .check(matches(not(isDisplayed())))
    }

    private fun verifyRetryDisplayed() {
        onView(withId(R.id.retry_text_view))
            .check(matches(isDisplayed()))
    }

    private fun verifyGoBackNotDisplayed() {
        onView(withId(R.id.go_back_button))
            .check(matches(not(isDisplayed())))
    }

    private fun verifyGoBackDisplayed() {
        onView(withId(R.id.go_back_button))
            .check(matches(isDisplayed()))
    }

    private fun verifyBitcoinChartIsDisplayed() {
        onView(withId(R.id.market_price_chart))
            .check(matches(isDisplayed()))
    }

    private fun performClickOnView(viewId: Int) {
        onView(withId(viewId)).perform(click())
    }

    private fun verifyButtonIsChecked(buttonId: Int) {
        onView(withId(buttonId)).check(matches(isChecked()))
    }

    private fun verifyButtonNotChecked(buttonId: Int) {
        onView(withId(buttonId)).check(matches(not(isChecked())))
    }

    private fun verifyBitcoinChartNotDisplayed() {
        onView(withId(R.id.market_price_chart))
            .check(matches(not(isDisplayed())))
    }

    private fun getStringResourceById(id: Int): String {
        val targetContext: Context = ApplicationProvider.getApplicationContext()
        return targetContext.resources.getString(id)
    }
}