package baller.example.hitchhikersweatherguidetoextraterrestialspace.activity

import HitchhikersWeatherGuideToExtraterrestialSpaceTheme
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import baller.example.hitchhikersweatherguidetoextraterrestialspace.R
import baller.example.hitchhikersweatherguidetoextraterrestialspace.ui.theme.HitchhikersWeatherGuideToExtraterrestialSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  viewModel : MainActivityViewModel = MainActivityViewModel()

        val navHostFragment =
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        /**
         * AJNFKJLlknlkNLKNLKNLKNLKNLKNALKAFNSKLFNLKASNFLKASNFLKASNFKLNA
         *
         *
         *
         *
         *
         *
         *
         *
         */

        enableEdgeToEdge()
        setContent {

            HitchhikersWeatherGuideToExtraterrestialSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun FragmentHost() {
    View(
        factory = { context ->
            // Create a FragmentContainerView programmatically
            FragmentContainerView(context).apply {
                id = View.generateViewId()

                // Host your Fragment here using FragmentManager
                val fragment = MyFragment() // Replace with your fragment
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(id, fragment)
                    .commit()
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HitchhikersWeatherGuideToExtraterrestialSpaceTheme {
        Greeting("Android")
    }
}