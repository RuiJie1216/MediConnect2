import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.data.dao.DoctorDao
import com.example.testasgn.ui.data.model.Doctor
import com.example.testasgn.ui.data.repository.DoctorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DoctorViewModel(private val repository: DoctorRepository) : ViewModel() {
    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> = _doctors

    init {
        viewModelScope.launch {
            _doctors.value = repository.getAllDoctors()
        }
    }

    suspend fun getDoctorById(id: String): Doctor? {
        return repository.getDoctorById(id)
    }
}