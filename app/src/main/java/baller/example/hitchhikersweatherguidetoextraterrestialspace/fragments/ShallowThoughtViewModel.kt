package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients.RESTClient
import baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients.WolframAlphaAPIClient
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaAPIRequest
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaAPIResponse
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaConversation
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaConversationHistory

class ShallowThoughtViewModel : ViewModel() {
    /**Represents the history of exchanges between the application and the API
     *  - Initiated once a conversationHistory is started (on first API-request)
     *
     *
     */
    lateinit var conversationHistory: WolframAlphaConversationHistory
    lateinit var listForCompose: MutableList<WolframAlphaConversation>
    lateinit var requestStringFromResponseView: String
    var selectedConversationId: MutableState<Int?> = mutableStateOf(null)

    /**Used to get any related content to the WolframAlphaAPI, private to enforce viewmodel-behaviour
     */
    private var apiClient = WolframAlphaAPIClient()
    private lateinit var currentApiResponse: WolframAlphaAPIResponse


    /**Gets a response from the WolframAlphaAPIClient, should only be called with some input actually in the TextField
     * @param requestStringFromResponseView
     */
    fun getResponse(requestStringFromResponseView: String): WolframAlphaAPIResponse {
        /*If it is the first conversation -> We just generate a api-response, then we initiate the conversationHistory*/
        if (!this::conversationHistory.isInitialized) {

            var wolframAlphaAPIRequest: WolframAlphaAPIRequest = WolframAlphaAPIRequest(
                previousAlphaAPIResponse = null,
                appId = null,
                query = requestStringFromResponseView
            )
            currentApiResponse = apiClient.getContent(
                RESTClient.HttpMethods.GET,
                mutableMapOf(Pair("wolframalphaAPIRequest", wolframAlphaAPIRequest))
            ) as WolframAlphaAPIResponse
            conversationHistory = WolframAlphaConversationHistory(
                WolframAlphaConversation(
                    requestStringFromResponseView,
                    0,
                    apiRequest = wolframAlphaAPIRequest,
                    currentApiResponse
                )
            )
            listForCompose = MutableList<WolframAlphaConversation>(
                init = {
                    return@MutableList conversationHistory[it]!!


                }, size = conversationHistory.size
            )
            return currentApiResponse
        }/*If not empty we check whether we have any history of the conversation, since then the conversationId is needed for the API*/
        else {
            /*Upon having initiated conversationHistory we can use the Id to select the appropriate conversation and
            * generate a new request using the WolframAlphaAPIRequest*/
            if (selectedConversationId != null) {
                conversationHistory[selectedConversationId.value].let {

                    val newAPIRequest: WolframAlphaAPIRequest =
                        WolframAlphaAPIRequest(it?.apiResponse, null, requestStringFromResponseView)
                    var newAPIResponse = apiClient.getContent(
                        RESTClient.HttpMethods.GET,
                        mutableMapOf(Pair("wolframalphaAPIRequest", newAPIRequest))
                    ) as WolframAlphaAPIResponse
                    selectedConversationId.value?.let { it1 ->
                        conversationHistory.set(
                            it1, WolframAlphaConversation(
                                selectedConversationId.value.toString(),
                                selectedConversationId.value!!, newAPIRequest, newAPIResponse
                            )
                        )
                    }
                    return newAPIResponse
                }
            }/*if the selectedConversationId == null, that means its a new conversation*/
            else {
                val newAPIRequest: WolframAlphaAPIRequest =
                    WolframAlphaAPIRequest(null, null, requestStringFromResponseView)
                var newAPIResponse = apiClient.getContent(
                    RESTClient.HttpMethods.GET,
                    mutableMapOf(Pair("wolframalphaAPIRequest", newAPIRequest))
                ) as WolframAlphaAPIResponse

                conversationHistory.addConversation(newAPIRequest, newAPIResponse)

                return newAPIResponse

            }

        }

    }


}