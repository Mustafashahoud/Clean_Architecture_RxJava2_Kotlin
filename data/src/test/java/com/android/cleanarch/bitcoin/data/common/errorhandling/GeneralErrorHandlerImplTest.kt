package com.android.cleanarch.bitcoin.data.common.errorhandling

import com.android.cleanarch.bitcoin.domain.base.errorhandling.ErrorEntity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@RunWith(JUnit4::class)
class GeneralErrorHandlerImplTest {

    private lateinit var generalErrorHandlerImpl: GeneralErrorHandlerImpl

    @Before
    fun setUp() {
        generalErrorHandlerImpl = GeneralErrorHandlerImpl()
    }


    @Test
    fun `test getError with IOException should get Network Error`() {
        val ioException = IOException()

        val errorEntity = generalErrorHandlerImpl.getError(ioException)

        assertThat(ErrorEntity.Network, `is`(errorEntity))
    }

    @Test
    fun `test getError with HttpException should get Server Error`() {
        val httpException = HttpException(Response.success("body"))

        val errorEntity = generalErrorHandlerImpl.getError(httpException)

        assertThat(ErrorEntity.Server, `is`(errorEntity))
    }

    @Test
    fun `test getError with general Exception should get Unknown Error`() {
        val exception = Exception()

        val errorEntity = generalErrorHandlerImpl.getError(exception)

        assertThat(ErrorEntity.Unknown, `is`(errorEntity))
    }

}