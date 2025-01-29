package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import HitchhikersWeatherGuideToExtraterrestialSpaceTheme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.util.fastRoundToInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api.InsightAPIResponse

/** Handles View (V-VM-M-pattern) for the Martian Weather API
 *
 */
class MartianWeatherFragment() : Fragment() {


    /**Lazy-generated ViewModel-object, upon need
     *
     */
    private val viewModel: MartianWeatherViewModel by viewModels()
    lateinit var currentWeatherReportList: MutableList<InsightAPIResponse.Sol?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Get weather reports and store them for use in the drawing of the weather-tabs*/
        currentWeatherReportList = viewModel.getContent()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        currentWeatherReportList = viewModel.getContent()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DrawWeatherReport()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DrawWeatherReport() {
        var currentSol =
            currentWeatherReportList[viewModel.selectedWeekTabIndex.intValue * 7 + viewModel.selectedMartianDayTabIndex.intValue]
        val wholeMartianWeeks = (currentWeatherReportList.size
                / 7.0.fastRoundToInt())
        HitchhikersWeatherGuideToExtraterrestialSpaceTheme {
            Column(Modifier.fillMaxSize()) {
                /*Where we display the martian weeks, with 7 in each*/

                PrimaryTabRow(
                    selectedTabIndex = remember { viewModel.selectedWeekTabIndex.intValue },
                    tabs = {
                        for (i in 0..<wholeMartianWeeks) {

                            Tab(
                                selected = (viewModel.selectedWeekTabIndex.intValue == i),
                                onClick = { viewModel.selectedWeekTabIndex.intValue = i },
                                content = { "Martian Week : ${i + 1}" }
                            )


                        }


                    })
                SecondaryTabRow(selectedTabIndex = remember { viewModel.selectedMartianDayTabIndex.intValue },
                    tabs = {


                        for (i in 0..6) {
                            /* (j*7+i) = indexnumber of current martian day */
                            Tab(selected = (viewModel.selectedMartianDayTabIndex.intValue == viewModel.selectedWeekTabIndex.intValue * 7 + i),
                                onClick = {
                                    viewModel.selectedMartianDayTabIndex.intValue =
                                        viewModel.selectedWeekTabIndex.intValue * 7 + i
                                },
                                content = {
                                    Text("Sol : ${viewModel.currentWeatherData.solKeyCollection[viewModel.selectedWeekTabIndex.intValue * 7 + i]}")

                                }
                            )


                        }


                    }
                )
            }
            /*Retains what page to show and what was shown last for any recomposition, will start a 0*/
            val martianDayPagerState: PagerState =
                rememberPagerState(0) { viewModel.selectedMartianDayTabIndex.intValue + viewModel.selectedWeekTabIndex.intValue * 7 }
            HorizontalPager(martianDayPagerState) {
                /*Used to change between sensor-readings*/
                var inFahrenheitIntValue: Int = (viewModel.inFahrenheit.value.let {
                    if (it) return@let 1 else return@let 0
                })


                TabRow(selectedTabIndex = viewModel.selectedSensorTabIndex.intValue,
                    tabs = {
                        /*A sol has four sensors : AtmosphericPressureReading, TemperatureReading, Wind-velocity/directionReading */
                        for (i in 0..3) {

                            Tab(selected = (viewModel.selectedSensorTabIndex.intValue == i),
                                onClick = { viewModel.selectedSensorTabIndex.intValue = i },
                                content = {
                                    /*Lays out different texts for the tabs indicating what sensor´s readings is displayed*/
                                    when (i) {
                                        0 -> Text("Atmospheric Pressure Reading : ")
                                        1 -> Text("Temperature Reading : ")
                                        2 -> Text("Horizontal wind speed : ")
                                        3 -> Text("Wind Direction Reading : ")
                                    }


                                })
                        }


                    }
                )
                /*We need as many as there area sensors, one for each available sensor per day - Only available, valid sensors will be exposed through the currentWeatherReport */
                /*In here we define the contents of a given MartianDayWeather-Page, the tab row should hold the different readings for ease of use*/
                when (viewModel.selectedSensorTabIndex.intValue) {
                    0 -> {

                        Text("Atmospheric Pressure Reading(Pa(kg/m^2)) \n \n \t Average: ${currentSol?.atmosphericPressureReading?.average} \n Max-value : ${currentSol?.atmosphericPressureReading?.maxValue} \n Min-value : ${currentSol?.atmosphericPressureReading?.minValue} \n Sensor-Count(Samples) : ${currentSol?.atmosphericPressureReading?.count}")


                    }

                    1 -> {
                        PrimaryTabRow(
                            inFahrenheitIntValue, tabs = {
                                for (i in 0..1) {
                                    Tab(selected = viewModel.inFahrenheit.value, onClick = {
                                        when (i) {
                                            0 -> {
                                                viewModel.inFahrenheit.value = false

                                            }

                                            1 -> {
                                                viewModel.inFahrenheit.value = true


                                            }


                                        }

                                    },
                                        content = {
                                            when (i) {

                                                0 -> Text("Celsius")
                                                1 -> Text("Fahrenheit")
                                            }
                                        }
                                    )
                                }

                            })
                        /*Lays out the values in Fahrenheit or Celsius depending on what is selected*/
                        if (viewModel.inFahrenheit.value) {
                            Column(Modifier.fillMaxSize()) {
                                Text(
                                    "Temperature readings:(degrees C)",
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxSize(0.3f)
                                )
                                Column(
                                    Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceAround,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Average temperature : ${currentSol?.temperatureReading?.asFahrenheit()?.average}")
                                    Text("Lowest Temperature : ${currentSol?.temperatureReading?.asFahrenheit()?.minValue}")
                                    Text("Highest Temperature : ${currentSol?.temperatureReading?.asFahrenheit()?.maxValue}")
                                    Text("Sensor-readings : ${currentSol?.temperatureReading?.asFahrenheit()?.count}")


                                }
                            }
                        } else {

                            Column {
                                Text(
                                    "Temperature readings:(degrees C)",
                                    Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxSize(0.3f)
                                )
                                Column(
                                    Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceAround,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Average temperature : ${currentSol?.temperatureReading?.asCelsius()?.average}")
                                    Text("Lowest Temperature : ${currentSol?.temperatureReading?.asCelsius()?.minValue}")
                                    Text("Highest Temperature : ${currentSol?.temperatureReading?.asCelsius()?.maxValue}")
                                    Text("Sensor-readings : ${currentSol?.temperatureReading?.asCelsius()?.count}")


                                }
                            }


                        }

                    }

                    2 -> {
                        Column {
                            Text(
                                "Horizontal wind speed readings: (m/s)",
                                Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxSize(0.3f)
                            )
                            Column(
                                Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Average wind-speed : ${currentSol?.horizontalWindSpeedReading?.average}")
                                Text("Lowest wind-speed : ${currentSol?.horizontalWindSpeedReading?.minValue}")
                                Text("Highest wind-speed : ${currentSol?.horizontalWindSpeedReading?.maxValue}")
                                Text("Sensor-readings : ${currentSol?.horizontalWindSpeedReading?.count}")


                            }
                        }


                    }

                    3 -> {
                        Column(Modifier.fillMaxSize(0.3f)) {
                            Text(
                                "Wind direction during sol:",
                                Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val textMeasurer = rememberTextMeasurer()

                            Canvas(modifier = Modifier.fillMaxSize()) {

                                /*We want the origin to be at the center of the Canvas*/
                                val canvasWidth = size.width
                                val canvasHeight = size.height
                                val canvasOriginXValue: Float = canvasWidth / 2.0f
                                val canvasOriginYValue: Float = canvasHeight / 2.0f
                                /*For each of the compass-points in the array we want to out the lines */
                                var totalCounts: Int = 0
                                if (currentSol != null) {
                                    for (compassPoint in currentSol.windDirectionReading!!.compassPointarray) {
                                        totalCounts += compassPoint.count

                                    }
                                }
                                for (i in currentSol?.windDirectionReading?.compassPointarray?.indices!!) {
                                    /*Draws out a proportional wind-direction-line*/
                                    var fractionOfTotalCounts: Float =
                                        (currentSol.windDirectionReading!!.compassPointarray[i].count) / totalCounts.toFloat()
                                    drawLine(
                                        start = Offset(
                                            x = canvasOriginXValue,
                                            y = canvasOriginYValue
                                        ),
                                        end = Offset(
                                            x = (currentSol.windDirectionReading!!.compassPointarray[i].compassRight.toFloat()) * fractionOfTotalCounts,
                                            y = (currentSol.windDirectionReading!!.compassPointarray[i].compassUp.toFloat()) * fractionOfTotalCounts
                                        ),
                                        color = Color.Red
                                    )
                                    var originOffset = Offset(
                                        x = canvasOriginXValue,
                                        y = canvasOriginYValue
                                    )

                                    var vectorOffset = Offset(
                                        x = (currentSol.windDirectionReading!!.compassPointarray[i].compassRight.toFloat()) * fractionOfTotalCounts,
                                        y = (currentSol.windDirectionReading!!.compassPointarray[i].compassUp.toFloat()) * fractionOfTotalCounts
                                    )

                                    var totalOffset = originOffset.plus(vectorOffset)
                                    var directionString: AnnotatedString =
                                        currentSol?.windDirectionReading?.compassPointarray!![i]?.compassPoint?.toString()
                                            ?.let { it1 ->
                                                AnnotatedString.Builder(it1).toAnnotatedString()
                                            }!!
                                    textMeasurer.measure(
                                        text = directionString.toString()
                                    )
                                    drawText(textMeasurer, directionString, topLeft = totalOffset)


                                }


                            }
                        }


                    }
                }


            }

        }

    }


}



