package com.example.testasgn

import android.os.Build
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.testasgn.ui.SignUpInfoScreen
import com.example.testasgn.ui.SignUpPwdScreen
import com.example.testasgn.ui.SignUpSuccessScreen
import com.example.testasgn.ui.data.repository.DoctorRepo
import com.example.testasgn.ui.data.repository.MedicalReminderRepo
import com.example.testasgn.ui.data.repository.UserRepo
import com.example.testasgn.ui.database.AppDatabase
import com.example.testasgn.ui.viewModel.AccViewModel
import com.example.testasgn.ui.docTheme.DocHomeScreen
import com.example.testasgn.ui.docTheme.DocPatientsScreen
import com.example.testasgn.ui.docTheme.DocProfileScreen
import com.example.testasgn.ui.loginTheme.DoctorLoginScreen
import com.example.testasgn.ui.loginTheme.UserLoginScreen
import com.example.testasgn.ui.theme.BalooTypography
import com.example.testasgn.ui.userTheme.UserAppointmentScreen
import com.example.testasgn.ui.userTheme.UserHomeScreen
import com.example.testasgn.ui.userTheme.UserMedicReminderScreen
import com.example.testasgn.ui.userTheme.UserPersonalInfoScreen
import com.example.testasgn.ui.userTheme.UserProfileScreen
import com.example.testasgn.ui.viewModel.MedicalReminderViewModel
import com.example.testasgn.ui.viewModel.SignUpViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class AppScreen {
    //System
    LoginSystem,
    UserSystem,
    DocSystem,
    SignUpSystem,

    //SignUp
    SignUpInfo,
    SignUpPwd,
    SignUpSuccess,

    //Login
    UserLogin,
    DocLogin,

    //User
    UserHome,
    UserAppointment,
    UserProfile,
    UserMedicalReminder,
    UserPersonalInfo,

    //Doctor
    DocHome,
    DocPatient,
    DocAppointment,
    DocProfile,
    DocEditProfile
}

class AccViewModelFactory(
    private val userRepo: UserRepo,
    private val doctorRepo: DoctorRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccViewModel(userRepo, doctorRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MedicalReminderViewModelFactory(
    private val medicalReminderRepo: MedicalReminderRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicalReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicalReminderViewModel(medicalReminderRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(
    currentScreen: AppScreen,
    searchQuery: String,
    onChangeSearchQuery: (String) -> Unit,
    hasNavigate: (AppScreen) -> Unit,
    onDocLogout: () -> Unit
) {
    when(currentScreen) {
        AppScreen.DocHome, AppScreen.UserHome, AppScreen.UserProfile, AppScreen.UserMedicalReminder, AppScreen.UserPersonalInfo -> {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "MEDICONNECT",
                            style = BalooTypography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00C8B3),
                    titleContentColor = Color.White
                )
            )
        }
        AppScreen.DocProfile -> {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(
                        onClick = { hasNavigate(AppScreen.DocHome) }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { AppScreen.DocEditProfile }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(
                        onClick = onDocLogout
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
        AppScreen.DocPatient -> {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = onChangeSearchQuery,
                        placeholder = { Text("Search patients...") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF29E6D2),
                            unfocusedContainerColor = Color(0xFF29E6D2),
                            disabledContainerColor = Color(0xFF29E6D2),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { hasNavigate(AppScreen.DocHome) }
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF29E6D2)
                )
            )
        }
        AppScreen.SignUpInfo, AppScreen.SignUpPwd -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Sign Up"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00C8B3),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { hasNavigate(AppScreen.LoginSystem) }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
        else -> {}
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediConnectApp(
    modifier: Modifier = Modifier,
    signUpViewModel: SignUpViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    //database for app
    val db = remember { AppDatabase.getInstance(context) }

    //repository
    val userRepo = remember { UserRepo(db.userDao()) }
    val doctorRepo = remember { DoctorRepo(db.doctorDao()) }
    val medicalReminderRepo = remember { MedicalReminderRepo(db.medicalDao()) }

    //factory of repo
    val accFactory = remember { AccViewModelFactory(userRepo, doctorRepo) }
    val medicalFactory = remember { MedicalReminderViewModelFactory(medicalReminderRepo) }

    //accViewModel
    val accViewModel: AccViewModel = viewModel(
        factory = accFactory
    )

    val medicalViewModel: MedicalReminderViewModel = viewModel(
        factory = medicalFactory
    )

    val currentUser by accViewModel.currentUser.collectAsState()
    val currentDoctor by accViewModel.currentDoctor.collectAsState()
    val reminders by medicalViewModel.reminders.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            medicalViewModel.loadAllReminders(user.ic)

            medicalViewModel.scheduleAllUserReminders(context)
        }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.LoginSystem.name
    )

    val signUpUiState by signUpViewModel.signUpUiState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var docLogOutConfirm by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopBarScreen(
                currentScreen = currentScreen,
                searchQuery = searchQuery,
                onChangeSearchQuery = {searchQuery = it},
                hasNavigate = {navController.navigate(it.name)},
                onDocLogout = {docLogOutConfirm = true}
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = AppScreen.LoginSystem.name,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            //LoginPage
            navigation(
                startDestination = AppScreen.UserLogin.name,
                route = AppScreen.LoginSystem.name
            ) {
                //UserLogin
                composable(route = AppScreen.UserLogin.name) {
                    var ic by rememberSaveable { mutableStateOf("") }
                    var pwd by rememberSaveable { mutableStateOf("") }
                    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

                    UserLoginScreen(
                        modifier = Modifier
                            .fillMaxHeight(),
                        chooseBar = currentScreen,
                        errorMessage = errorMessage,
                        ic = ic,
                        pwd = pwd,
                        onChangeIc = {
                            ic = it
                            if (errorMessage?.contains("IC") == true) {
                                errorMessage = null
                            }
                                     },
                        onchangePwd = {
                            pwd = it
                            if (errorMessage?.contains("Error") == true) {
                                errorMessage = null
                            }
                                      },
                        onLoginClick = {
                            errorMessage = null
                            accViewModel.userLogin(ic, pwd) { success, message ->
                                if (success) {
                                    navController.navigate(AppScreen.UserSystem.name)
                                } else {
                                    errorMessage = message
                                    if (errorMessage?.contains("IC") == true) {
                                        ic = ""
                                        pwd = ""
                                    }
                                    else {
                                        pwd = ""
                                    }
                                }
                            }
                        },
                        onTurnDoctorClick = {
                            navController.navigate(AppScreen.DocLogin.name)
                        },
                        onSignUpClick = {
                            signUpViewModel.reset()
                            navController.navigate(AppScreen.SignUpSystem.name)
                        },
                        onForgetPwdClick = {}
                    )
                }

                //DoctorLogin
                composable(route = AppScreen.DocLogin.name) {
                    var id by rememberSaveable { mutableStateOf("") }
                    var pwd by rememberSaveable { mutableStateOf("") }
                    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

                    DoctorLoginScreen(
                        modifier = Modifier
                            .fillMaxHeight(),
                        id = id,
                        pwd = pwd,
                        chooseBar = currentScreen,
                        errorMessage = errorMessage,
                        onChangeId = {
                            id = it
                            if (errorMessage?.contains("ID") == true) {
                                errorMessage = null
                            }
                                     },
                        onChangePwd = {
                            pwd = it
                            if (errorMessage?.contains("Error") == true) {
                                errorMessage = null
                            }
                                      },
                        onForgetPwdClick = {},
                        onTurnUsersClick = {
                            navController.navigate(AppScreen.UserLogin.name)
                        },
                        onLoginClick = {
                            errorMessage = null
                            accViewModel.docLogin(id, pwd) { success, message ->
                                if (success) {
                                    navController.navigate(AppScreen.DocSystem.name)
                                } else {
                                    errorMessage = message
                                    if (errorMessage?.contains("ID") == true) {
                                        id = ""
                                        pwd = ""
                                    }
                                    else {
                                        pwd = ""
                                    }
                                }
                            }
                        }


                    )
                }
            }

            //Sign Up
            navigation(
                startDestination = AppScreen.SignUpInfo.name,
                route = AppScreen.SignUpSystem.name
            ) {
                composable(route = AppScreen.SignUpInfo.name) {
                    var existPatient by rememberSaveable { mutableStateOf<Boolean?>(signUpUiState.existPatient) }
                    var ic by rememberSaveable { mutableStateOf(signUpUiState.ic) }
                    var name by rememberSaveable { mutableStateOf(signUpUiState.name) }
                    var email by rememberSaveable { mutableStateOf(signUpUiState.email) }
                    var phone by rememberSaveable { mutableStateOf(signUpUiState.phone) }
                    var read by rememberSaveable { mutableStateOf(signUpUiState.read) }
                    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

                    val icRegex = Regex("^\\d{12}$")
                    val passportRegex = Regex("^[A-PR-WYa-pr-wy][1-9]\\d{5,8}$")
                    val phoneRegex = Regex("^01[0-9]{8,9}$")


                    SignUpInfoScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        existPatient = existPatient,
                        onChangeExistPatient = {existPatient = it},
                        ic = ic,
                        onChangeIc = {
                            ic = it
                            if (errorMessage?.contains("IC") == true) {
                                errorMessage = null
                            }
                                     },
                        name = name,
                        onChangeName = {name = it},
                        email = email,
                        onChangeEmail = {
                            email = it
                            if (errorMessage?.contains("Email") == true) {
                                errorMessage = null
                            }
                                        },
                        phone = phone,
                        onChangePhone = {
                            phone = it
                            if (errorMessage?.contains("Phone") == true) {
                                errorMessage = null
                            }
                                        },
                        read = read,
                        onChangeRead = {read = it},
                        errorMessage = errorMessage,
                        onNextClick = {
                            if (!ic.matches(icRegex) && !ic.matches(passportRegex)) {
                                errorMessage = "Invalid IC"
                            }
                            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                errorMessage = "Invalid Email"
                            }
                            else if (!phone.matches(phoneRegex)) {
                                errorMessage = "Invalid Phone"
                            }
                            else {
                                accViewModel.userCheck(ic, email, phone) { success, message ->
                                    if (success) {
                                        signUpViewModel.setExistPatient(existPatient?: false)
                                        signUpViewModel.setIc(ic)
                                        signUpViewModel.setName(name)
                                        signUpViewModel.setEmail(email)
                                        signUpViewModel.setPhone(phone)
                                        signUpViewModel.setRead(read)

                                        navController.navigate(AppScreen.SignUpPwd.name)
                                    } else {
                                        errorMessage = message
                                    }
                                }
                            }
                        }
                    )
                }

                composable(route = AppScreen.SignUpPwd.name) {
                    var newPwd by rememberSaveable { mutableStateOf("") }
                    var confirmPwd by rememberSaveable { mutableStateOf("") }
                    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }


                    SignUpPwdScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        newPwd = newPwd,
                        onChangeNewPwd = {newPwd = it},
                        confirmPwd = confirmPwd,
                        onChangeConfirmPwd = {
                            confirmPwd = it
                            if (errorMessage?.contains("Passwords do not match") == true) {
                                errorMessage = null
                            }
                                             },
                        errorMessage = errorMessage,
                        onBackClick = {
                            navController.navigate(AppScreen.SignUpInfo.name)
                                      },
                        onConfirmClick = {
                            errorMessage = null
                            if (newPwd != confirmPwd) {
                                confirmPwd = ""
                                errorMessage = "Passwords do not match"
                            }
                            else {
                                signUpViewModel.setPwd(newPwd)
                                val updateState = signUpUiState.copy(pwd = newPwd)
                                accViewModel.userSignUp(updateState) { success, message ->
                                    if (success) {
                                        navController.navigate(AppScreen.SignUpSuccess.name)
                                    } else {
                                        errorMessage = message
                                    }
                                }
                            }

                        }
                    )
                }

                composable(route = AppScreen.SignUpSuccess.name) {
                    SignUpSuccessScreen(
                        modifier = modifier
                            .fillMaxSize(),
                        onBackClick = { navController.navigate(AppScreen.LoginSystem.name) }
                    )
                }
            }

            //DoctorPage
            navigation(
                startDestination = AppScreen.DocHome.name,
                route = AppScreen.DocSystem.name
            ){
                //Home
                composable(route = AppScreen.DocHome.name) {
                    DocHomeScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        onAppointmentClick = {},
                        onProfileClick = {
                            navController.navigate(AppScreen.DocProfile.name)
                        },
                        onPatientClick = {
                            navController.navigate(AppScreen.DocPatient.name)
                        }
                    )
                }

                //PatientInfo
                composable(route = AppScreen.DocPatient.name) {
                    DocPatientsScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        searchQuery = searchQuery,
                        onAddClick = {},
                        onPatientDetailClick = {},
                        onBackClick = {
                            navController.navigate(AppScreen.DocHome.name)
                        }
                    )
                }
                
                composable(route = AppScreen.DocProfile.name) {
                    if (docLogOutConfirm) {
                        AlertDialog(
                            onDismissRequest = { docLogOutConfirm = false },
                            title = { Text("Confirm Logout") },
                            text = { Text("Are you sure you want to logout?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    docLogOutConfirm = false
                                    navController.navigate(AppScreen.LoginSystem.name)
                                }) { Text("Yes") }
                            },
                            dismissButton = {
                                TextButton(onClick = { docLogOutConfirm = false }) { Text("No") }
                            }
                        )
                    }
                    DocProfileScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        doctor = currentDoctor
                    )
                }
            }

            //UserPage
            navigation(
                startDestination = AppScreen.UserHome.name,
                route = AppScreen.UserSystem.name
            ){
                //Home
                composable(route = AppScreen.UserHome.name) {
                    UserHomeScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        chooseBar = currentScreen,
                        onAppointmentClick = {navController.navigate(AppScreen.UserAppointment.name)},
                        onProfileClick = {navController.navigate(AppScreen.UserProfile.name)},
                        onMedicalReminderClick = {navController.navigate(AppScreen.UserMedicalReminder.name)}
                    )
                }

                composable(route = AppScreen.UserProfile.name) {
                    var logOutConfirm by rememberSaveable { mutableStateOf(false) }

                    if (logOutConfirm) {
                        AlertDialog(
                            onDismissRequest = { logOutConfirm = false },
                            title = { Text(text = "Confirm Logout") },
                            text = { Text("Are you sure you want to logout?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    logOutConfirm = false
                                    navController.navigate(AppScreen.LoginSystem.name)
                                }) {
                                    Text("Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { logOutConfirm = false }) {
                                    Text("No")
                                }
                            },
                            properties = DialogProperties(dismissOnClickOutside = false)
                        )
                    }

                    UserProfileScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        currentUser = currentUser,
                        currentScreen = currentScreen,
                        onHomeClick = {navController.navigate(AppScreen.UserHome.name)},
                        onAppointmentClick = {navController.navigate(AppScreen.UserAppointment.name)},
                        onLogoutClick = {
                            logOutConfirm = true
                        },
                        onPersonalInfoClick = {navController.navigate(AppScreen.UserPersonalInfo.name)}
                    )
                }

                composable(route = AppScreen.UserAppointment.name) {

                }

                composable(route = AppScreen.UserMedicalReminder.name) {
                    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

                    LaunchedEffect(selectedDate) {
                        medicalViewModel.loadRemindersByDate(
                            userIc = currentUser?.ic ?: "",
                            date = selectedDate.toString()
                        )
                    }

                    val sortedReminders = remember(reminders) {
                        reminders.sortedBy {
                            LocalTime.parse(it.time, DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))
                        }
                    }

                    UserMedicReminderScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        selectedDate = selectedDate,
                        onChangeSelectedDate = {selectedDate = it},
                        reminders = sortedReminders,
                        currentScreen = currentScreen,
                        onHomeClick = {navController.navigate(AppScreen.UserHome.name)},
                        onProfileClick = {navController.navigate(AppScreen.UserProfile.name)},
                        onAppointmentClick = {navController.navigate(AppScreen.UserAppointment.name)}
                    )
                }

                composable(route = AppScreen.UserPersonalInfo.name) {
                    var name by remember { mutableStateOf(currentUser?.name ?: "") }
                    var age by remember { mutableStateOf(currentUser?.age?.toString() ?: "") }
                    var gender by remember { mutableStateOf(currentUser?.gender ?: "") }
                    var email by remember { mutableStateOf(currentUser?.email ?: "") }
                    var address by remember { mutableStateOf(currentUser?.address ?: "") }
                    var phone by remember { mutableStateOf(currentUser?.phone ?: "") }
                    var medicalHistory by remember { mutableStateOf(currentUser?.medicalHistory ?: "") }
                    var weight by remember { mutableStateOf(currentUser?.weight?.toString() ?: "") }
                    var height by remember { mutableStateOf(currentUser?.height?.toString() ?: "") }

                    UserPersonalInfoScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        name = name,
                        onChangeName = {name = it},
                        age = age,
                        onChangeAge = {age = it},
                        gender = gender,
                        onChangeGender = {gender = it},
                        email = email,
                        onChangeEmail = {email = it},
                        phone = phone,
                        onChangePhone = {phone = it},
                        address = address,
                        onChangeAddress = {address = it},
                        weight = weight,
                        onChangeWeight = {weight = it},
                        height = height,
                        onChangeHeight = {height = it},
                        medicalHistory = medicalHistory,
                        onChangeMedicalHistory = {medicalHistory = it},
                        onSaveClick = {
                            val updateState = currentUser?.copy(
                                name = name,
                                age = age.toIntOrNull() ?: 0,
                                gender = gender,
                                email = email,
                                phone = phone,
                                address = address,
                                medicalHistory = medicalHistory,
                                weight = weight.toDoubleOrNull() ?: 0.0,
                                height = height.toDoubleOrNull() ?: 0.0
                            )
                            updateState?.let {
                                accViewModel.userUpdate(it)
                            }
                            navController.navigate(AppScreen.UserProfile.name)
                        },
                        onBackClick = {navController.navigate(AppScreen.UserProfile.name)}
                    )
                }
            }


        }
    }
}



