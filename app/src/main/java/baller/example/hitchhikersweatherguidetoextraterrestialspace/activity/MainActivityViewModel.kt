package baller.example.hitchhikersweatherguidetoextraterrestialspace.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**Is responsible for ViewModel-specific(V-VM-M) handling in accordanace with the view-viewmodel-model
 * -pattern
 *
 * Since MainAcitivity holds the responsibility of transitioning and keeping track of the latest state of shown fragments
 * the latest showing
 */
class MainActivityViewModel : ViewModel() {

    private val _sharedData = MutableLiveData<HashMap<String,Any?>>()
    val sharedData: LiveData<HashMap<String,Any?>> = _sharedData

    // Function to update the shared data
    fun updateSharedData() {
    }



}
