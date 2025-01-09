package baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api

/**Designates the cardinal + intercardinal directions (16 ordinal)
 *
 *  [N,NNE....NW,NNW]
 *  ; For use in the WindDirectionReading which utilizes this system.
 *
 */
enum class CardinalDirectionEnumInsightAPI (val direction: String) {
    N("North"),
    NNE("North-Northeast"),
    NE("Northeast"),
    ENE("East-Northeast"),
    E("East"),
    ESE("East-Southeast"),
    SE("Southeast"),
    SSE("South-Southeast"),
    S("South"),
    SSW("South-Southwest"),
    SW("Southwest"),
    WSW("West-Southwest"),
    W("West"),
    WNW("West-Northwest"),
    NW("Northwest"),
    NNW("North-Northwest");
}


