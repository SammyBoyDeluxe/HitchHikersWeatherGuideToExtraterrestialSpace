package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import androidx.lifecycle.ViewModel
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaConversationHistory

class ShallowThoughtViewModel : ViewModel() {
    /**Represents the history of exchanges between the application and the API
     *  - Initiated once a conversationHistory is started (on first API-request)
     *
     *
     */
    lateinit var conversationHistory: WolframAlphaConversationHistory
}