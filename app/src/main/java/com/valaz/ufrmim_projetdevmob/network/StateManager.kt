package com.valaz.ufrmim_projetdevmob.network

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object StateManager {
    @OptIn(DelicateCoroutinesApi::class)
    fun launchCoroutine(block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            block()
        }
    }

}