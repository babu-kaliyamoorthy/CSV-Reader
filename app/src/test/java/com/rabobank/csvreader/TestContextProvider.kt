package com.rabobank.csvreader

import com.rabobank.csvreader.base.CoroutinesContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Babu Kaliyamoorthy on 25/11/19.
 */
class TestContextProvider : CoroutinesContextProvider() {
    override val Main: CoroutineContext = Dispatchers.Unconfined
    override val IO: CoroutineContext = Dispatchers.Unconfined
}