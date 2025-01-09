package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import baller.example.hitchhikersweatherguidetoextraterrestialspace.R
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_insight_api.InsightAPIResponse
import java.time.Instant

/** Handles View (V-VM-M-pattern) for the Martian Weather API
 *
 */
class MartianWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = MartianWeatherFragment()
    }

    private val viewModel: MartianWeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_martian_weather, container, false)
    }

    @Composable
    fun drawWeatherReport(weekDays : List<String>) {

        val selectedTabIndex : MutableIntState = viewModel.selectedTabIndex


        Column {
            TabRow(selectedTabIndex = selectedTabIndex.value) {
                for() {
                    Tab(
                        selected = false
                                onClick = { },
                        content = DrawWeatherTab(selectedTabIndex)
                    )
                       }
            }



        }


    }
    @Composable
    fun DrawWeatherTab(selectedTabIndex: Int, validSolArray: Array<InsightAPIResponse.Sol>) {
        if (validSolArray != null) {
            val selectedSol = validSolArray.get(selectedTabIndex)

            if (selectedSol != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Weather Information (Sol ${viewModel.currentWeatherData.solKeyCollection.get(selectedTabIndex)})", // Display the latest Sol key
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Box(modifier = Modifier.padding(8.dp)
                        ){
                        Column {
                        Box() {
                            Row {
                                Text(
                            text = "Season: ${selectedSol.season?.name}",
                            style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                            text = "First Time instant with valid reading: ${selectedSol.firstUTC.toString()}",
                            style = MaterialTheme.typography.bodyMedium
                                    )
                            }

                            Row {
                                Text(
                            text = "Select Units: ",
                            style = MaterialTheme.typography.bodyMedium
                                    )
                                Box(){
                                Switch(checked = viewModel.inFahrenheit.value,{ b: Boolean -> viewModel.inFahrenheit.value = b})
                                }
                                Box {
                                    Switch(checked = viewModel.inMetric.value,{ b: Boolean -> viewModel.inMetric.value = b})

                                }
                        }

                    // Display weather data from the latest Sol
                    selectedSol.temperatureReading?.let { tempReading ->
                        if(viewModel.inFahrenheit.value){

                        tempReading.asFahrenheit()
                        Text("Temperature (avg): ${tempReading.average}°F")
                        Text("Temperature (min): ${tempReading.minValue}°F")
                        Text("Temperature (max): ${tempReading.maxValue}°F")
                        }
                        else{
                         val readingInCelsius : InsightAPIResponse.Sol.TemperatureReading = tempReading.asCelsius()


                            Text("Temperature (avg): ${readingInCelsius.average}°C")
                            Text("Temperature (min): ${readingInCelsius.minValue}°C")
                            Text("Temperature (max): ${readingInCelsius.maxValue}°C")

                        }
                    }

                    selectedSol.horizontalWindSpeedReading?.let { windReading ->
                        Text("Wind Speed (avg): ${windReading.average} m/s")
                    }

                    selectedSol.atmosphericPressureReading?.let { pressureReading ->
                        Text("Pressure (avg): ${pressureReading.average} Pa")
                    }

                    selectedSol.windDirectionReading?.let { windDirectionReading ->
                        val mostCommonDirection = windDirectionReading.compassPointarray.maxByOrNull { it.count }?.compassPoint
                        Text("Most Common Wind Direction: ${mostCommonDirection?.name}")
                    }

                    // Add more weather parameters as needed
                }
            } else {
                // Handle case where no Sol data is available
                Text("No weather data available.")
            }
        }
    }


}