package baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients

import java.net.HttpURLConnection

interface RESTClient {
    /**Gives the base host url upon which we perform our actions on endpoints therein
     *
     */
     val baseUrl : String

    /**A string with the API-key, if such is needed for the API-implementation
     * ; Otherwise null
     */
    val apikey : String?

    /**Represents getting content on a map of parameter values and their associated keys
     *  relevant to the API
     *
     * -> The RESTclient performs the action on the baseurl with set parameters.
     *
     * Returns a baller.example.... .apiclients.APIResponse object
     *
     */
    public abstract fun getContent(action : HttpMethods, apiParamsKeyValueMap : Map<String,Any?>) : APIResponse

    /**Can be used to set authorization, whether it be via APIkey, SSL/TSL-configuration
     * or the like
     */
    public abstract fun setAuthorization()

    /**Gets a connection to the API-Url, can be directly cast as
     *  HTTPsUrlConnection
     */
    fun getConnection() : HttpURLConnection

    /**
     *      Represents the HTTP-actions associated with HTTP and HTTPS-requests
     *
     *      Members of companion-object are to be used as the action-parameter in
     *      RESTClient.getContent()
     *
     */
    enum class HttpMethods(stringIn : String) {

             GET("GET"),
             POST("POST"),
             HEAD("HEAD"),
             OPTIONS("OPTIONS"),
             PUT("PUT"),
             DELETE("DELETE"),
             TRACE("TRACE");

    }
}