package baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients

import android.util.Log
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api.InsightAPIResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import javax.net.ssl.HttpsURLConnection

/** The NasaInsightAPIClient (extends) is a type of RESTClient (see : abstract super-class)
 *  : It handles parsing and organization of data from the REST-API via a JSON-class structure,
 *  represented by the NasaInsightAPIResponse : APIResponse
 */
class NASAInsightAPIClient(
    override val baseUrl: String = "https://api.nasa.gov/insight_weather/?feedtype=json&ver=1.0",
    override val apikey: String? = "aFRTG7YOW56OpdB5Fc4DBVkPuCfu9Hybz8y1qub4"
) : RESTClient  {


    /*Recommended course of action from java.net documentation was to keep a URI and pass it as a URL
    * when making a connection to the source, instigated in constructor*/
    lateinit var apiURI : URI
     var apiConnection : HttpsURLConnection? = null

    init {
        apiURI =  URI(baseUrl)
    }

    /**Returns an InsightAPIResponse object parsed from the API-call if connection is successful,
     *  returns an empty InsightAPIResponse instance otherwise = InsightAPIResponse(emptyList(), emptyList(), emptyArray())
     */


    override fun getContent(
        action: RESTClient.HttpMethods,
        apiParamsKeyValueMap: Map<String, Any?>
    ): APIResponse {
        var connection : HttpsURLConnection = getConnection() as HttpsURLConnection

        try
            {
            /*request-method, sets the HTTP-action, always get on NasaInsightAPI*/
            connection.requestMethod = action.toString()
                /*Not needed, for clarity - The doInput-field represents the HTTPs-request ability to getInput (from upstream datastream) */
            connection.doInput = true
                /*If we recieve a valid response, we return that response*/
            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.use { it.readText() } // Read the entire stream as a string


               return APIResponse.fromJsonToAPIResponse(response, InsightAPIResponse::class.java)
            }
            /*Else we return the HTTPS-failure-code*/
            else {
                Log.d("applicationfailure", "getContent(NASAInsightAPIClient - Line : 41)  : Error: Received HTTP response code ${connection.responseCode}")

            }
        } catch (e: Exception) {
            Log.d("applicationfailure", "getContent() (NasaInsigtAPIClient) :\n Error: ${e.message}")


        } finally {
            /*Finally-blocks always run, no matter the results of previous try/catch-blocks hence we disconnect, this prevents memory leakage*/
            connection.disconnect()
        }
        return InsightAPIResponse(emptyList(), emptyList(), emptyArray())




    }


    /**Sets the client´s API-key header, only called in getConnection to
     *  since it can only be set when trying to make a request
     */
    override fun setAuthorization() {
        apiConnection?.setRequestProperty("x-api-key",apikey!!)
    }

    /**Returns a HttpsUrlConnection to the baseURL of the API, with a set API-key header
     *
     */
    override fun getConnection() : HttpURLConnection {
        /*The Insight API is a JSON-formatted API. As such we recieve a JSON-feed, complete with JSON-objects
     To connect to the database we need to establish an HTTPS-urlConnection**/
        apiConnection  = apiURI.toURL().openConnection() as HttpsURLConnection
        /*Only set if apiConnection exists*/
        this.setAuthorization()
        return apiConnection as HttpsURLConnection



    }
}