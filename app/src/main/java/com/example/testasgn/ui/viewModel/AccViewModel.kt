package com.example.testasgn.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testasgn.ui.data.DataTable.Doctors
import com.example.testasgn.ui.data.uiState.SignUpUiState
import com.example.testasgn.ui.data.DataTable.Users
import com.example.testasgn.ui.data.repository.DoctorRepo
import com.example.testasgn.ui.data.repository.UserRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
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
                                address = userDoc.getString("address") ?: ""
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
                                phone = doctorDoc.getString("phone") ?: ""
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
}