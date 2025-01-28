package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api

import java.net.URL
import java.net.URLEncoder

/**Generates a WolframAlphaAPIRequest, containing the relevant params WolframAlphaRESTAPIClient.for getContent()
 *
 */
class WolframAlphaAPIRequest(
    /**Takes the last response of the conversation to expose its relevant attributes (s-value?,error-value?,conversationId)
     *  -> A conversation (subsequent request/response-pairs in relation to a specific query ) always starts with a API-response,
     *  left as null if first query in conversation
     *
     *
     */
    val previousAlphaAPIResponse: WolframAlphaAPIResponse?,
    /**
     * - Represents one of two values needed to make an API-request : appId (apiKey in WolframAlphaAPIClient) (and query as the second value)
     *
     * Is not needed if a api-request (instance of this class) is passed to the getContent()-function in the WolframAlphaAPIClient
     */
    var appId: String?,
    /**
     * Represents one of two values needed to make an API-request : query (The query we want the API to run)
     */
    private val query: String
) {
    /**
     *
     */
    val params = mutableMapOf<String, String>()

    init {
        params["appid"] = appId.toString()
        params["i"] = URLEncoder.encode(query, "UTF-8")
        /*default case : metric*/
        params["units"] = "metric"
        if (previousAlphaAPIResponse != null) {
            if (previousAlphaAPIResponse.s != null) {
                params["s"] = previousAlphaAPIResponse.s
                params["conversationID"] = previousAlphaAPIResponse.conversationId
            }
        }
    }

    /**
     * geolocation
     *
     * This parameter lets you specify a latitude/longitude pair like "40.42,-3.71". Although this is the most exact way to specify a location, it will fail if no valid data is available nearby for the query. Negative latitude values indicate south, and negative longitude values indicate west:
     *
     * http://api.wolframalpha.com/v1/conversation.jsp?appid=DEMO&i=What+is+the+nearest+lake%3f&geolocation=40.11,-88.28
     *
     * {"result" : "The answer is Homer Lake",
     *  "conversationID" : "MSP6081cd79fgi58bd5f7400001586c30h5d1h7571",
     * "host" : "www4b.wolframalpha.com"}
     *
     */
    fun setGeolocation(latitude: Double, longitude: Double) {
        params["geolocation"] = "$latitude,$longitude"
    }

    /**
     * ip
     *
     * Set the IP address of the caller, which will be used to determine a location. If you are forwarding calls from your own web visitors to the API, you can propagate their IP addresses with this parameter:
     *
     * http://api.wolframalpha.com/v1/conversation.jsp?appid=DEMO&i=What+is+the+high+temperature+today%3f&ip=203.0.113.10
     *
     * {"result" : "The answer is 69 degrees Fahrenheit",
     * "conversationID" : "MSP3811gaeg0eeiih56eg0000068iehc21b6ia0aa6",
     * "host" : "www4b.wolframalpha.com"}
     */
    fun setIpAddress(ip: String) {
        params["ip"] = ip
    }

    /**(Default case :)true = units in metric
     *                false = units in imperial
     */
    fun setUnits(inMetric: Boolean) {
        if (inMetric) params["units"] = "metric"
        else
            params["units"] = "imperial"
    }

    /**
     * s
     *
     * This parameter should only be used in the rare case that your result includes the s output parameter:
     *
     * http://api.wolframalpha.com/v1/conversation.jsp?appid=DEMO&i=When+was+Isaac+Newton+born%3f
     *
     * {"result" : "Isaac Newton was born on Sunday, December 25, 1642",
     * "conversationID" : "MSP7451bi53ge536cadca8000054ihb2h764g39d2g",
     * "host" : "www4b.wolframalpha.com",
     * "s" : "3"}
     *
     */
    fun setSParameter(sParam: String) {
        params["s"] = URLEncoder.encode(sParam, "UTF-8")

    }

    /**Builds a ready to go requestURL for the WolframAlphaAPIClient
     *  -> Specific parameters can be set via setFunctions() ; exposing all available parameters
     *
     *  Will link to the history of the previous conversation (query-request-response-sequence) if previousAPIResponse is provided,
     *  or start a new conversation otherwise by calling the base Wolfram Alpha API-url
     */
    fun buildRequestUrl(): URL {
        var baseUrl: String
        if (previousAlphaAPIResponse == null) baseUrl =
            "http://api.wolframalpha.com/v1/conversation.jsp"
        else baseUrl = "http://${previousAlphaAPIResponse.hostUrl}/v1/conversation.jsp"
        val queryString = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        return URL("$baseUrl?$queryString")
    }
}