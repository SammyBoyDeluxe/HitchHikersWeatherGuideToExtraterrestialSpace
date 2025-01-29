package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api

/**Object to hold a collection of saved WolframAlphaConversations defined by the id-int of the WolframAlphaConversation
 *
 */
class WolframAlphaConversationHistory(
    firstConversation: WolframAlphaConversation,
    override var size: Int = 1
) : Map<Int, WolframAlphaConversation> {

    // Backing mutable map to hold the conversations
    private val conversationHistory: MutableMap<Int, WolframAlphaConversation> = mutableMapOf()

    init {
        // Initialize the map with the first conversation
        conversationHistory[0] = firstConversation
    }

    override val entries: Set<Map.Entry<Int, WolframAlphaConversation>>
        get() = conversationHistory.entries

    override val keys: Set<Int>
        get() = conversationHistory.keys

    override val values: Collection<WolframAlphaConversation>
        get() = conversationHistory.values

    override fun containsKey(key: Int): Boolean {
        return conversationHistory.containsKey(key)
    }

    override fun containsValue(value: WolframAlphaConversation): Boolean {
        return conversationHistory.containsValue(value)
    }

    override fun get(key: Int): WolframAlphaConversation? {
        return conversationHistory[key]
    }

    override fun isEmpty(): Boolean {
        return conversationHistory.isEmpty()
    }

    /**Returns a stringarray of the titles arranged in the order of their arrangement
     * -> returnTitleArray(id) = titleOfConversation
     */
    fun returnTitles(): Array<String> {
        // Map the values of the conversations to their titles and return as an array
        return conversationHistory.values.map { it.title }.toTypedArray()
    }

    /**Adds a conversation to conversationHistory-object and gives it an internal id
     * of the next possible integer
     */
    fun addConversation(apiRequest: WolframAlphaAPIRequest, apiResponse: WolframAlphaAPIResponse) {
        conversationHistory[size] =
            WolframAlphaConversation("${size}", id = size, apiRequest, apiResponse)
        size++
    }

    operator fun set(selectedConversationId: Int, value: WolframAlphaConversation) {
        this[selectedConversationId] = value
    }
}
