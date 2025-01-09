package baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients

import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaAPIRequest
import java.net.HttpURLConnection
import java.net.URI
import javax.net.ssl.HttpsURLConnection

class WolframAlphaAPIClient : RESTClient {
    override val baseUrl: String

    /**In the case of the Wolfram Alpha-API, the value of the app-id parameter
     *
     */
    override val apikey: String?
    /*URI -> URL via Android Studio best practices*/
    lateinit var  apiURI : URI
    constructor(baseUrl: String, apikey: String?) {
        this.baseUrl = "http://api.wolframalpha.com/v1/conversation.jsp"
        this.apikey = "53GA4K-H4PU6Q34PJ"
        apiURI = URI(baseUrl)
    }

    /**Exposes the current APIrequest for handling in the client
     *
     */
    lateinit var currentAPIRequest : WolframAlphaAPIRequest

    /** action is an irrelevant parameter in WolframAlphaAPI-Client, since the API only handles gets.
     *
     *  -> Only api-parameter needed to be given is under : "wolframalphaAPIRequest",
     *  generating such a request exposes all possible and needed parameters
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
    override fun getContent(
        action: RESTClient.HttpMethods,
        apiParamsKeyValueMap: Map<String, Any?>
    ): APIResponse {
         currentAPIRequest = apiParamsKeyValueMap["wolframalphaAPIRequest"] as WolframAlphaAPIRequest
        setAuthorization()
        val connection : HttpsURLConnection = getConnection() as HttpsURLConnection


    }

    override fun setAuthorization() {
        currentAPIRequest.appId = apikey!!

    }

    override fun getConnection(): HttpURLConnection {
       return (currentAPIRequest.buildRequestUrl()).openConnection() as HttpsURLConnection
    }
}