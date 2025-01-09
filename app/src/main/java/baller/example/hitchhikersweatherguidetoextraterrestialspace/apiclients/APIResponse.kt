package baller.example.hitchhikersweatherguidetoextraterrestialspace.apiclients

import com.google.gson.Gson

/**Represents the JSON-structure of an API-response, for any JSON-typed API.
 *
 * Contains methods to handle parsing to and from JSON using the Gson-library
 *
 */
open abstract class APIResponse {

    companion object {
        private val gsonObject: Gson = Gson()

        /**
         * Takes a JSON-response and returns an object of the specified APIResponse subclass.
         *
         * @param jsonResponse JSON string to parse.
         * @param responseClass Class of the APIResponse subclass for parsing.
         * @return An instance of the specified APIResponse subclass.
         */
        fun <T : APIResponse> fromJsonToAPIResponse(
            jsonResponse: String,
            responseClass: Class<T>
        ): T {
            return gsonObject.fromJson(jsonResponse, responseClass)
        }

        /**
         * Turns an APIResponse subclass object into its corresponding JSON string.
         *
         * @param apiResponse An instance of the APIResponse subclass.
         * @return JSON string representation of the APIResponse object.
         */
        fun <T : APIResponse> fromAPIResponseToJson(apiResponse: T): String {
            return gsonObject.toJson(apiResponse)
        }
    }


    }
}