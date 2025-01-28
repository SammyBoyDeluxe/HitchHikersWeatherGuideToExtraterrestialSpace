@file:OptIn(ExperimentalMaterial3Api::class)

package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import baller.example.hitchhikersweatherguidetoextraterrestialspace.R
import baller.example.hitchhikersweatherguidetoextraterrestialspace.data_wolfram_alpha_api.WolframAlphaAPIResponse

class ShallowThoughtFragment : Fragment() {

    companion object {
        fun newInstance() = ShallowThoughtFragment()
    }

    private val viewModel: ShallowThoughtViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                /*Draws a background with the Menu-image as background, on a black colored background, with -3f,-4f respectively*/
                drawBackground()


            }
        }
    }

    /*Draws a background on which we can display*/
    @Composable
    fun drawBackground() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(listOf(Color.Black)),
                    shape = RectangleShape,
                    1f
                )
                .zIndex(-4f)
        ) {

            val imageBrush =
                ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.hitchhikers_triangle_rocketlogo)))
            /*Draws up the backgroundImage*/
            Image(
                BrushPainter(imageBrush),
                "Background image of a triangle with a rocket on",
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(-3f)
            )


        }


    }

    @Composable
            /**Takes in an API-response, gotten from the view-model, otherwise the functions accepts null to
             * show a blank screen with a welcome-text
             *
             */
    fun responseView(apiResponse: WolframAlphaAPIResponse?) {
        var responseString: String = remember { "" }
        var requestString: String = remember { "" }
        Column(
            Modifier
                .fillMaxSize(0.6f)
                .background(SolidColor(Color.Gray)),

            ) {      /*The String representing what is shown on the response-screen*/
            if (apiResponse != null) {
                /*If we have a response we should either show the result from that response, otherwise we show*/
                if (apiResponse.error != null) {
                    responseString = apiResponse.error.toString()
                } else if (apiResponse.result != "") {
                    responseString = apiResponse.result

                }
            } else responseString = R.string.shallow_thought_welcome_text.toString()

            Row {
                OutlinedTextField(value = "", onValueChange = { requestString = it }


                )
                OutlinedButton(onClick = {

                    if (requestString.trim().isNotEmpty()) viewModel.getResponse(requestString)

                }) {
                    Text("Send request")

                }
            }


        }


    }

    /**Given null if conversationHistory isn´t initiated, in that case viewModel is updated
     *
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun conversationHistorySelector(conversationId: Int?) {

        if (ShallowThoughtViewModel::conversationHistory) {
            PrimaryTabRow(
                selectedTabIndex = 0,
                tabs = {

                    Tab(
                        selected = 0,
                        onClick = { viewModel.selectedConversationId = 0 },
                        content = {
                            Text("Conversation 1")

                        }
                    )


                }
            )
        }

    }
}