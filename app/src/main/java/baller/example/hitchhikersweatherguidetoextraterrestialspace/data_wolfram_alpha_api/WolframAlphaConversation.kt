package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api

/**A class intended to save WolframAlphaConversations, an id and a user defined title to identify them
 *
 */
class WolframAlphaConversation(
    var title: String,
    val id: Int,
    var apiRequest: WolframAlphaAPIRequest,
    var apiResponse: WolframAlphaAPIResponse
) {
}