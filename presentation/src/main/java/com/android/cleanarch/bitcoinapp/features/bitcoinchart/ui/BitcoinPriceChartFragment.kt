package com.android.cleanarch.bitcoinapp.features.bitcoinchart.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.cleanarch.bitcoinapp.R
import com.android.cleanarch.bitcoinapp.common.GoBackCallback
import com.android.cleanarch.bitcoinapp.common.RetryCallback
import com.android.cleanarch.bitcoinapp.databinding.FragmentBitcoinPriceChartBinding
import com.android.cleanarch.bitcoinapp.di.Injectable
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity.TimeSpanOption
import com.android.cleanarch.bitcoinapp.features.bitcoinchart.model.BitcoinChartViewEntity.TimeSpanOption.*
import com.android.cleanarch.bitcoinapp.util.autoCleared
import com.google.android.material.button.MaterialButtonToggleGroup
import javax.inject.Inject

class BitcoinPriceChartFragment : Fragment(R.layout.fragment_bitcoin_price_chart), Injectable {

    private var binding by autoCleared<FragmentBitcoinPriceChartBinding>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: BitcoinPriceChartViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentBitcoinPriceChartBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            retryCallback = object : RetryCallback {
                override fun retry() {
                    viewModel.retry()
                }
            }
            goBackCallback = object : GoBackCallback {
                override fun goBack() {
                    activity?.onBackPressed()
                }

            }
        }

        setupUserInteractionsListeners()
        subscribeResults()
    }

    private fun subscribeResults() {
        viewModel.bitcoinChartLiveData.observe(viewLifecycleOwner) { binding.resource = it }
    }

    private fun setupUserInteractionsListeners() {
        binding.timeSpanButtonGroup.addOnButtonCheckedListener { materialButtonToggleGroup, _, isChecked ->
            getSelectedTimeSpanOption(materialButtonToggleGroup)?.let { timeSpanOption ->
                if (!isChecked)
                viewModel.notifyTimeSpanChanged(timeSpanOption.stringValue)
            }
        }
    }


    private fun getSelectedTimeSpanOption(group: MaterialButtonToggleGroup): TimeSpanOption? {
        return when (group.checkedButtonId) {
            R.id.thirty_days_button -> THIRTY_DAYS
            R.id.three_months_button -> THREE_MONTHS
            R.id.one_year_button -> ONE_YEAR
            R.id.all_time_button -> ALL_TIME
            else -> null
        }
    }


}