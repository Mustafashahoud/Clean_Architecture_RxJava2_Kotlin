package com.android.cleanarch.bitcoin.domain.bitcoinchart.interactor


import com.android.cleanarch.bitcoin.domain.base.SingleUseCase
import com.android.cleanarch.bitcoin.domain.bitcoinchart.model.BitcoinChartModel
import com.android.cleanarch.bitcoin.domain.bitcoinchart.repository.BitcoinChartRepository
import com.petertackage.kotlinoptions.Option
import io.reactivex.Single
import javax.inject.Inject

class GetBitcoinChart @Inject constructor(
    private val bitcoinChartRepository: BitcoinChartRepository,
) : SingleUseCase<BitcoinChartModel, String>() {

    override fun buildUseCase(params: Option<String>): Single<BitcoinChartModel> {
        return bitcoinChartRepository.getBitcoinChart(params.getUnsafe())
    }


}