package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients.NASAInsightAPIClient
import baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients.RESTClient
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api.InsightAPIResponse

/**Handles ViewModel-related tasks via V-VM-M(View-ViewModel-Model)
 * -pattern
 */
class MartianWeatherViewModel(var fragment :MartianWeatherFragment) : ViewModel() {
    /**Represents the (currently) selected tab´s index
     * in the weather-report Composable-function
     *
     */
    var selectedTabIndex = mutableIntStateOf(0)

    /**The client for the NASA-insight-APIA
     *
     */
    val nasaInsightAPIClient : NASAInsightAPIClient = NASAInsightAPIClient()

    /**Represents whether units are shown in metric or imperial
     *
     */
    var inMetric  = mutableStateOf(false)

    /**
     * Represents whether units are shown in fahrenheit or celsius
     */
    var inFahrenheit = mutableStateOf(true)


   lateinit var currentWeatherData : InsightAPIResponse




    /**Returns all of the valid sols inside an InsightAPIResponse, the response to the request with the specified parameters (does not work due to API-
     * being out of use)
     *
     */
    fun getContent(): MutableList<InsightAPIResponse.Sol?> {
        val apiParamsKeyValueMap: Map<String, Any?> = getSearchParameters(this.fragment)
        /*Sets the currentWeatherData-variable to the latest, valid response*/
        currentWeatherData = nasaInsightAPIClient.getContent(RESTClient.HttpMethods.GET,apiParamsKeyValueMap) as InsightAPIResponse
        /*A string arrat holding the index of valid sols (martian days) inside the request*/
        var validSolKeys = currentWeatherData!!.solKeyCollection

        /*All sols (martian days) inside the request, to be filtered for valid ones to display on UI*/
        val solArray = currentWeatherData!!.solCollection.toTypedArray()
        /*Represents validity-checked, valid sols (martian days) inside the request*/
        var validSolMutableList: MutableList<InsightAPIResponse.Sol?> = arrayOfNulls<InsightAPIResponse.Sol>(validSolKeys.size).toMutableList()
      return  getValidSols(validSolKeys, validSolMutableList, solArray)
        /*All valid sols have been input into the validsolsArray - data should be posted*/




    }

    /**
     * Gets all the elements specificed by indices in validSolKeys, from the solArray and puts them into the
     * in the validSolArray,  generating a MutableList<InsightAPIResponse.Sol?> of all valid sols in the response
     */
    private fun getValidSols(
        validSolKeys: Array<String>,
        validSolArray: MutableList<InsightAPIResponse.Sol?>,
        solArray: Array<InsightAPIResponse.Sol>
    ): MutableList<InsightAPIResponse.Sol?> {
        for (validSolKey in validSolKeys) {
            /*adds a valid sol-value to to the valid sol-array*/
            validSolArray.add(solArray[validSolKey.toInt()])

        }

        return validSolArray
    }

    /**Gets the relevant search-parameters from the input-elements of the MartianWeatherFragment
     *
     */
    fun getSearchParameters(fragment: MartianWeatherFragment) : Map<String, Any?>{





    }







}