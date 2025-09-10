package com.example.testasgn.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.data.dataTable.Doctors
import com.example.testasgn.ui.data.uiState.SignUpUiState
import com.example.testasgn.ui.data.dataTable.Users
import com.example.testasgn.ui.data.repository.DoctorRepo
import com.example.testasgn.ui.data.repository.UserRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccViewModel(
    private val userRepo: UserRepo,
    private val doctorRepo: DoctorRepo
): ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _currentUser = MutableStateFlow<Users?>(null)
    val currentUser: StateFlow<Users?> = _currentUser

    private val _currentDoctor = MutableStateFlow<Doctors?>(null)
    val currentDoctor: StateFlow<Doctors?> = _currentDoctor

    fun syncFirebaseToRoom(onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                // Step 1: Sync Users
                db.collection("users").get()
                    .addOnSuccessListener { snapshot ->
                        viewModelScope.launch {
                            snapshot.documents.forEach { doc ->
                                val user = Users(
                                    ic = doc.getString("ic") ?: "",
                                    name = doc.getString("name") ?: "",
                                    email = doc.getString("email") ?: "",
                                    phone = doc.getString("phone") ?: "",
                                    height = doc.getDouble("height") ?: 0.0,
                                    weight = doc.getDouble("weight") ?: 0.0,
                                    age = doc.getLong("age")?.toInt() ?: 0,
                                    address = doc.getString("address") ?: "",
                                    gender = doc.getString("gender") ?: "",
                                    medicalHistory = doc.getString("medicalHistory") ?: "-"
                                )
                                userRepo.insert(user)
                            }
                        }
                    }

                // Step 2: Sync Doctors
                db.collection("doctors").get()
                    .addOnSuccessListener { snapshot ->
                        viewModelScope.launch {
                            snapshot.documents.forEach { doc ->
                                val doctor = Doctors(
                                    id = doc.getString("id") ?: "",
                                    name = doc.getString("name") ?: "",
                                    email = doc.getString("email") ?: "",
                                    phone = doc.getString("phone") ?: "",
                                    degree = doc.getString("degree") ?: "",
                                    specialty = doc.getString("specialty") ?: "",
                                    year = doc.getLong("year")?.toInt() ?: 0,
                                    currentHospital = doc.getString("current_hospital") ?: "",
                                    quote = doc.getString("quote") ?: "",
                                    language = (doc.get("languages") as? List<*>)?.joinToString(", ") {
                                        it as? String ?: ""
                                    } ?: "",
                                    dayOff = (doc.get("day_off") as? List<*>)?.joinToString(", ") {
                                        it as? String ?: ""
                                    } ?: ""
                                )
                                doctorRepo.insert(doctor)
                            }
                        }
                    }

                onComplete(true, "Firebase data synced to Room")

            } catch (e: Exception) {
                onComplete(false, "Error: ${e.message}")
            }
        }
    }

    fun userCheck(ic: String, email: String, phone: String,onResult: (Boolean, String) -> Unit) {
        db.collection("users")
            .whereEqualTo("ic", ic)
            .get()
            .addOnSuccessListener { icSnapshot ->
                if (!icSnapshot.isEmpty) {
                    onResult(false, "IC already exists")
                    return@addOnSuccessListener
                }

                db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { emailSnapshot ->
                        if (!emailSnapshot.isEmpty) {
                            onResult(false, "Email already exists")
                            return@addOnSuccessListener
                        }

                        db.collection("users")
                            .whereEqualTo("phone", phone)
                            .get()
                            .addOnSuccessListener { phoneSnapshot ->
                                if (!phoneSnapshot.isEmpty) {
                                    onResult(false, "Phone already exists")
                                    return@addOnSuccessListener
                                }
                                onResult(true, "Success")
                            }
                    }
            }
    }

    fun userSignUp(signUpUiState: SignUpUiState, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(signUpUiState.email, signUpUiState.pwd)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userMap = mapOf(
                        "ic" to signUpUiState.ic,
                        "name" to signUpUiState.name,
                        "email" to signUpUiState.email,
                        "phone" to signUpUiState.phone
                    )
                    db.collection("users").document()
                        .set(userMap)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                userRepo.insert(
                                    Users(
                                        ic = signUpUiState.ic,
                                        name = signUpUiState.name,
                                        email = signUpUiState.email,
                                        phone = signUpUiState.phone
                                    )
                                )
                            }

                            onResult(true, "Sign up successfully")
                        }
                        .addOnFailureListener { exception ->
                            onResult(false, "Error: ${exception.message}")
                        }
                } else {
                    onResult(false, "Error: ${task.exception?.message}")
                }
            }
    }

    fun userLogin(ic: String, inputPwd: String, onResult: (Boolean, String) -> Unit) {
        db.collection("users")
            .whereEqualTo("ic", ic)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    onResult(false, "IC not found")
                    return@addOnSuccessListener
                }

                val userDoc = snapshot.documents[0]
                val email = userDoc.getString("email") ?: ""

                auth.signInWithEmailAndPassword(email, inputPwd)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val user = Users(
                                ic = userDoc.getString("ic") ?: "",
                                name = userDoc.getString("name") ?: "",
                                email = userDoc.getString("email") ?: "",
                                phone = userDoc.getString("phone") ?: "",
                                height = userDoc.getDouble("height") ?: 0.0,
                                weight = userDoc.getDouble("weight") ?: 0.0,
                                age = userDoc.getLong("age")?.toInt() ?: 0,
                                address = userDoc.getString("address") ?: "",
                                gender = userDoc.getString("gender") ?: "",
                                medicalHistory = userDoc.getString("medicalHistory") ?: ""
                            )

                            viewModelScope.launch {
                                userRepo.insert(user)
                                _currentUser.value = user
                            }

                            onResult(true, "Login successfully: ${auth.currentUser?.uid}")
                        } else {
                            onResult(false, "Error: ${task.exception?.message}")
                        }
                    }
            }
            .addOnFailureListener { exception ->
                onResult(false, "Error: ${exception.message}")
            }
    }

    fun docLogin(id: String, inputPwd: String, onResult: (Boolean, String) -> Unit) {
        db.collection("doctors")
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { snapshots ->
                if (snapshots.isEmpty) {
                    onResult(false, "ID not found")
                    return@addOnSuccessListener
                }

                val doctorDoc = snapshots.documents[0]
                val email = doctorDoc.getString("email") ?: ""

                auth.signInWithEmailAndPassword(email, inputPwd)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val doctor = Doctors(
                                id = doctorDoc.getString("id") ?: "",
                                name = doctorDoc.getString("name") ?: "",
                                email = doctorDoc.getString("email") ?: "",
                                phone = doctorDoc.getString("phone") ?: "",
                                degree = doctorDoc.getString("degree") ?: "",
                                specialty = doctorDoc.getString("specialty") ?: "",
                                year = doctorDoc.getLong("year")?.toInt() ?: 0,
                                currentHospital = doctorDoc.getString("current_hospital") ?: "",
                                quote = doctorDoc.getString("quote") ?: "",
                                language = (doctorDoc.get("languages") as? List<*>)?.joinToString(", ") { it as? String ?: "" } ?: "",
                                dayOff = (doctorDoc.get("day_off") as? List<*>)?.joinToString(", ") { it as? String ?: "" } ?: ""
                            )

                            viewModelScope.launch {
                                doctorRepo.insert(doctor)
                                _currentDoctor.value = doctor
                            }

                            onResult(true, "Login successfully: ${auth.currentUser?.uid}")
                        } else {
                            onResult(false, "Error: ${task.exception?.message}")
                        }
                    }
            }
            .addOnFailureListener { exception ->
                onResult(false, "Error: ${exception.message}")
            }
    }

    fun userUpdate(user: Users) {
        viewModelScope.launch {
            userRepo.update(user)
            _currentUser.value = user

            db.collection("users")
                .whereEqualTo("ic", user.ic)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        val docRef = snapshot.documents[0].reference

                        docRef.update(
                            mapOf(
                                "name" to user.name,
                                "email" to user.email,
                                "phone" to user.phone,
                                "age" to user.age,
                                "address" to user.address,
                                "gender" to user.gender,
                                "weight" to user.weight,
                                "height" to user.height,
                                "medicalHistory" to user.medicalHistory
                            )
                        ).addOnSuccessListener {
                            println("Firebase update success for IC: ${user.ic}")
                        }.addOnFailureListener { e ->
                            println("Firebase update failed: ${e.message}")
                        }
                    } else {
                        println("No user found with IC: ${user.ic}")
                    }
                }
                .addOnFailureListener { e ->
                    println("Firebase query failed: ${e.message}")
                }

        }
    }
}