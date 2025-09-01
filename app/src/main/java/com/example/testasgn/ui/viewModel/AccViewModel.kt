package com.example.testasgn.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.testasgn.ui.data.SignUpUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

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
        db.collection("users")
            .whereEqualTo("ic", signUpUiState.ic)
            .get()
            .addOnSuccessListener { icSnapshot ->
                if (!icSnapshot.isEmpty) {
                    onResult(false, "Error: IC already exists")
                    return@addOnSuccessListener
                }

                db.collection("users")
                    .whereEqualTo("email", signUpUiState.email)
                    .get()
                    .addOnSuccessListener { emailSnapshot ->
                        if (!emailSnapshot.isEmpty) {
                            onResult(false, "Error: Email already exists")
                            return@addOnSuccessListener
                        }

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