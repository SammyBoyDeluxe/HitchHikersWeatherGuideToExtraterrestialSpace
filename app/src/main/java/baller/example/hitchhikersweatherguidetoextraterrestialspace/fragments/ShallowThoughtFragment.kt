@file:OptIn(ExperimentalMaterial3Api::class)

package baller.example.hitchhikersweatherguidetoextraterrestialspace.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
                DrawBackground()
                Button(onClick = {
                    findNavController().navigate(R.id.action_shallowThoughtFragment_to_menuFragment)

                }) { Text("Back to menu") }
                ConversationHistorySelector()
                /*Automatically updated via mutablestate, set to null at start and then the latest APIResponse thereafter*/
                ResponseView(viewModel.currentAPIResponseState.value)


            }
        }
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to [Activity.onStart] of the containing
     * Activity's lifecycle.
     */
    override fun onStart() {
        super.onStart()

    }

    /*Draws a background on which we can display*/
    @Composable
    fun DrawBackground() {

        Box(
            modifier = with(Modifier) {
                fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.hitchhikersgreenmanlogo),
                        contentScale = FillBounds
                    )

            })


    }


    @Composable
            /**Handles the interaction with the VM , otherwise the functions accepts null to
             * show a blank screen with a welcome-text
             *
             */
    fun ResponseView(apiResponse: WolframAlphaAPIResponse? = remember { null }) {
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
                    if (requestString.trim().isNotEmpty()) {
                        /*generates a new currentApiRequest that can be used in the */
                        viewModel.getResponse(requestString)
                    }

                }) {
                    Text("Send request")

                }
            }


        }


    }

    /**Depends internally on the MutableState<Boolean> conversationHistoryIsInitialized, only rendering
     * a conversationHistorySelector if there is a conversation-history
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ConversationHistorySelector() {

        if (viewModel.conversationHistoryIsInitalized.value) {
            viewModel.selectedConversationId.value?.let {
                PrimaryTabRow(
                    selectedTabIndex = it,
                    tabs = {
                        for (i in 0..<viewModel.conversationHistory.size) {
                            Tab(
                                selected = (viewModel.selectedConversationId.value == i),
                                onClick = { viewModel.selectedConversationId.value = i },
                                content = {
                                    Text("Conversation ${i}")

                                }
                            )
                        }

                    }
                )
            }
        }

    }
}