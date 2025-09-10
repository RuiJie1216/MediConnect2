import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testasgn.ui.data.model.ConsultationRecord
import com.example.testasgn.ui.viewModel.ConsultationViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultationScreen(
    patientId: String,
    onAddRecord: (ConsultationRecord) -> Unit,
    onFinish: () -> Unit
) {
    val records by viewModel.getRecords(patientId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultation Records") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF29E6D2),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            records.sortedByDescending { it.index }.forEach { record ->
                ConsultationCard(record = record)
            }


            Spacer(modifier = Modifier.height(16.dp))
            DoctorInputSection(
                patientId = patientId,
                onAdd = onAddRecord,
                onFinish = onFinish
            )
        }
    }
}
@Composable
fun ConsultationCard(record: ConsultationRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Consultation #${record.index}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text("${record.date} ${record.time}", color = Color.Gray)



            showSection("Chief Complaint", record.chiefComplaint)
            showSection("HPI", record.historyOfPresentIllness)
            showSection("Past Medical History", record.pastMedicalHistory)
            showSection("Medication History", record.medicationHistory)
            showSection("Vital Signs", record.vitalSigns)
            showSection("Physical Examination", record.physicalExamination)
            showSection("Assessment/Diagnosis", record.assessmentDiagnosis)
            showSection("Investigation", record.investigation)
            showSection("Plan & Treatment", record.planTreatment)
            showSection("Follow-ups", record.followUps)
        }
    }
}
@Composable
fun showSection(title: String, list: List<String>) {
    if (list.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontWeight = FontWeight.Bold)
        list.forEach { item -> Text("• $item") }
    }
}
@Composable
fun DoctorInputSection(
    patientId: String,
    onAdd: (ConsultationRecord) -> Unit,
    onFinish: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Chief Complaint") }
    var inputText by remember { mutableStateOf("") }

    Column {
        // 分类选择
        CategorySelector(selectedCategory) { newSelection ->
            selectedCategory = newSelection
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter details") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // ➕ 添加按钮
            Button(onClick = {
                val now = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

                val newRecord = ConsultationRecord(
                    patientId = patientId,
                    index = (0..9999).random(),
                    date = now,
                    time = time,
                    chiefComplaint = if (selectedCategory == "Chief Complaint") listOf(inputText) else emptyList(),
                    historyOfPresentIllness = if (selectedCategory == "HPI") listOf(inputText) else emptyList(),
                    medicationHistory = if (selectedCategory == "Medication") listOf(inputText) else emptyList(),
                    followUps = if (selectedCategory == "Follow-up") listOf(inputText) else emptyList()
                )

                onAdd(newRecord)
                inputText = ""
            }) {
                Text("Add Record")
            }

            OutlinedButton(
                onClick = onFinish,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                border = BorderStroke(1.dp, Color.Red)
            ) {
                Text("End Consultation")
            }
        }
    }
}
@Composable
fun CategorySelector(selected: String, onSelected: (String) -> Unit) {
    val options = listOf("Chief Complaint", "HPI", "Medication", "Follow-up")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            OutlinedButton(
                onClick = { onSelected(option) },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selected == option) Color(0xFF29E6D2) else Color.Transparent,
                    contentColor = if (selected == option) Color.White else Color.Black
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selected == option) Color(0xFF29E6D2) else Color.Gray
                )
            ) {
                Text(option)
            }
        }
    }
}
