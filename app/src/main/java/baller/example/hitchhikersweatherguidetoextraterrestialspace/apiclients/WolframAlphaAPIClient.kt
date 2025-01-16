package baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients

import android.util.Log
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaAPIRequest
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaAPIResponse
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
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
     *  Upon successful handling we obtain a JSON-cast WolframAlphaAPIResponse from the HTTP-response,
     *  else we get: {WolframAlphaAPIResponse(
     *                 result = "",
     *                 conversationId = "",
     *                 hostUrl = URL(null),
     *                 s = null,
     *                 error = null
     *             )}
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
        /*Integrates the latest request into the API-Client structure, where setAuthorization and getConnection is dependent on this variable*/
         currentAPIRequest = apiParamsKeyValueMap["wolframalphaAPIRequest"] as WolframAlphaAPIRequest
        setAuthorization()
        val connection : HttpsURLConnection = getConnection() as HttpsURLConnection

        /*URL constructed via the APIRequest-class holds the parameters needed*/
        try{
            connection.connect()
            /*If there´s a failure for some reason, this codeblock executes*/
            if(connection.responseCode != HttpsURLConnection.HTTP_OK){
                Log.d("appfail", "getContent: line 59 ${connection.responseCode}" )
            
            } else if(connection.responseCode == HttpsURLConnection.HTTP_OK){
                /*Content type = JSON-stream, a string*/
                val jsonResponse = connection.getContent() as String

                return APIResponse.fromJsonToAPIResponse(jsonResponse, WolframAlphaAPIResponse::class.java)



            }
        } catch (exception : Exception){

                Log.d("appfail", "getContent (WolframAlphaAPIClient): + Exception thrown: ${exception.message} ")
        }
        finally {
            /*Always disconnect to prevent resource-leak*/
            connection.disconnect()
        }
        /*Will only run if previous return-statement is not executed*/
        return WolframAlphaAPIResponse(
            result = "",
            conversationId = "",
            hostUrl = URL(null),
            s = null,
            error = null
        )
    }

    /**Sets the appid to the equivalently functioning Api-key
     *
     */
    override fun setAuthorization() {
        currentAPIRequest.appId = apikey!!

    }

    /**Returns a connection to the API-source, unconnected
     *  based on the URL-specified in the  current (latest) request
     *
     */
    override fun getConnection(): HttpURLConnection {
       return (currentAPIRequest.buildRequestUrl()).openConnection() as HttpsURLConnection
    }
}