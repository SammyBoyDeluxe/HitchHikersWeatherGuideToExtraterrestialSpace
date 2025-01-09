package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api

import baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients.APIResponse
import com.google.gson.annotations.SerializedName
import java.net.URL

/**Is used to represent the content of the Wolfram Alpha API JSON-response
 *
 *
 */
data class WolframAlphaAPIResponse(
    /**                                     result

                 The latest text response from Wolfram|Alpha in the current conversation.
                 The API will take this value into account when computing an answer to the next query in this conversation.
     *
      */
    @SerializedName("result") val result: String,
    /**
     *      A string that serves as a unique identifier for the current conversation. Passing this value into a subsequent query (using the conversationid input parameter) will continue the current conversation,
     *      computing a result based on both the new query and the previous result.
     */
    @SerializedName("conversationID") val conversationId: String,
    /**
     *  The URL of the specific Wolfram|Alpha server processing the current conversation, to be used as the base URL for followup queries.
     *  This value should stay the same throughout a conversation.
     */
    @SerializedName("host") val hostUrl: URL,
    /**
     *  This parameter only appears in rare circumstances.
     *  When present, this value must be passed into the s parameter to continue a conversation.
     */
                                    @SerializedName("s") val s: String?,

                                    @SerializedName("error") val error: WolframAlphaErrorEnum? ) : APIResponse() {


    /**Represents the errors that can be thrown by the WolframAlphaConversationalAPI
     *
     */
    enum class WolframAlphaErrorEnum(error: String?) {
        /**This value for the error parameter is returned if a given input value cannot be interpreted by this API. This is commonly caused by input that is misspelled, poorly formatted or otherwise unintelligible. Because this API is designed to return a single result, this message may appear if no sufficiently short result can be found.
         * You may occasionally receive this status when requesting information on topics that are restricted or not covered.
         *
         */
        NO_RESULTS_AVAILABLE("No result is available"),
        /**This value for the error parameter indicates that the API did not find an input parameter while parsing. In most cases, this can be fixed by checking that you have used the correct syntax for including the i parameter.
         *
         */
        NO_INPUT("No input."),
        /**This error is returned when a request contains an invalid option for the appid parameter. Double-check that your AppID is typed correctly and that your appid parameter is using the correct syntax.
         *
         */
        APP_ID_ERROR1("Error 1: Invalid appid"),
        /**This error is returned when a request does not contain any option for the appid parameter. Double-check that you have entered an AppID and that your appid parameter is using the correct syntax.
         *
         */
        APP_ID_ERROR2("Error 2: Appid missing");

    }
}
