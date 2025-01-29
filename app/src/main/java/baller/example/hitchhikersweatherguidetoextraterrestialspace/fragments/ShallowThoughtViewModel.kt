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
    internal var currentAPIResponseState: MutableState<WolframAlphaAPIResponse?> =
        mutableStateOf(null)
    internal var currentConversationHistoryState: MutableState<WolframAlphaConversationHistory?> =
        mutableStateOf(null)
    var conversationHistoryIsInitalized = mutableStateOf(this::conversationHistory.isInitialized)
    var apiResponseIsInitialized = mutableStateOf(this::currentApiResponse.isInitialized)


    /**Gets a response from the WolframAlphaAPIClient, should only be called with some input actually in the TextField
     * @param requestStringFromResponseView Will update the internal currentAPIResponseState, currentConversationHistoryState, for use in compose, before calling getResponse() for the first time
     * -> The (currentApiResponseState, currentConversationHistoryState) == null
     */
    fun getResponse(requestStringFromResponseView: String) {
        /*If it is the first conversation -> We just generate a api-response, then we initiate the conversationHistory*/
        if (!conversationHistoryIsInitalized.value) {

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
            /*After having created the conversationHistory we want to add it as a mutable state so the conversation-history-selector can react dynamically*/
            currentConversationHistoryState = mutableStateOf(conversationHistory)
            listForCompose = MutableList<WolframAlphaConversation>(
                init = {
                    return@MutableList conversationHistory[it]!!


                }, size = conversationHistory.size
            )
            /*Sets the selected-conversation-id to 0*/
            selectedConversationId.value = 0
            currentAPIResponseState = mutableStateOf(currentApiResponse)
        }/*If the history is initialized that means it is not our first conversation, if the selectedConversationId > conversationHistory.size we want to create a new conversation */
        else {
            /*Upon having initiated conversationHistory we can use the Id to select the appropriate conversation and
            * generate a new request using the WolframAlphaAPIRequest*/
            if (selectedConversationId.value != null) {
                conversationHistory[selectedConversationId.value].let {
                    /*Generates a new API-request with the previous response as template for associated conversation-attributes*/
                    var newAPIRequest: WolframAlphaAPIRequest =
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
                    currentConversationHistoryState = mutableStateOf(conversationHistory)
                    currentApiResponse = newAPIResponse
                    currentAPIResponseState = mutableStateOf(currentApiResponse)

                }
            }/*if the selectedConversationId == null, that means its a new conversation*/
            else {
                val newAPIRequest: WolframAlphaAPIRequest =
                    WolframAlphaAPIRequest(null, null, requestStringFromResponseView)
                var newAPIResponse = apiClient.getContent(
                    RESTClient.HttpMethods.GET,
                    mutableMapOf(Pair("wolframalphaAPIRequest", newAPIRequest))
                ) as WolframAlphaAPIResponse
                /*Add the conversation to the conversation-history for further prompting*/
                conversationHistory.addConversation(newAPIRequest, newAPIResponse)
                currentConversationHistoryState = mutableStateOf(conversationHistory)
                /*Sets the currentAPIResponse to the new Api-response*/
                currentApiResponse = newAPIResponse
                currentAPIResponseState = mutableStateOf(currentApiResponse)


            }

        }

    }


}