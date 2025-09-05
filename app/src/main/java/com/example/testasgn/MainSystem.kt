package com.example.testasgn

import android.util.Patterns
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
import com.example.testasgn.ui.viewModel.AccViewModel
import com.example.testasgn.ui.docTheme.DocHomeScreen
import com.example.testasgn.ui.docTheme.DocPatientsScreen
import com.example.testasgn.ui.docTheme.ProfileScreen
import com.example.testasgn.ui.loginTheme.DoctorLoginScreen
import com.example.testasgn.ui.loginTheme.UserLoginScreen
import com.example.testasgn.ui.theme.BalooTypography
import com.example.testasgn.ui.userTheme.UserHomeScreen
import com.example.testasgn.ui.viewModel.SignUpViewModel

enum class AppScreen {
    //System
    LoginSystem,
    UserSystem,
    DocSystem,
    SignUpSystem,

    //SignUp
    SignUpInfo,
    SignUpPwd,

    //Login
    UserLogin,
    DocLogin,

    //User
    UserHome,

    //Doctor
    DocHome,
    DocPatient,
    DocProfile,
    DocAppointment
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(
    currentScreen: AppScreen,
    searchQuery: String,
    onChangeSearchQuery: (String) -> Unit,
    hasNavigate: (AppScreen) -> Unit
) {
    when(currentScreen) {
        AppScreen.DocHome -> {
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

@Composable
fun MediConnectApp(
    modifier: Modifier = Modifier,
    accViewModel: AccViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.LoginSystem.name
    )

    val signUpUiState by signUpViewModel.signUpUiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarScreen(
                currentScreen = currentScreen,
                searchQuery = searchQuery,
                onChangeSearchQuery = {searchQuery = it},
                hasNavigate = {navController.navigate(it.name)}
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
                    var ic by remember { mutableStateOf("") }
                    var pwd by remember { mutableStateOf("") }
                    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                    var id by remember { mutableStateOf("") }
                    var pwd by remember { mutableStateOf("") }
                    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                    var existPatient by remember { mutableStateOf<Boolean?>(signUpUiState.existPatient) }
                    var ic by remember { mutableStateOf(signUpUiState.ic) }
                    var name by remember { mutableStateOf(signUpUiState.name) }
                    var email by remember { mutableStateOf(signUpUiState.email) }
                    var phone by remember { mutableStateOf(signUpUiState.phone) }
                    var read by remember { mutableStateOf(signUpUiState.read) }
                    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                    var newPwd by remember { mutableStateOf("") }
                    var confirmPwd by remember { mutableStateOf("") }
                    var errorMessage by remember { mutableStateOf<String?>(null) }


                    SignUpPwdScreen(
                        modifier = modifier
                            .fillMaxHeight(),
                        newPwd = newPwd,
                        onChangeNewPwd = {newPwd = it},
                        confirmPwd = confirmPwd,
                        onChangeConfirmPwd = {confirmPwd = it},
                        onBackClick = {
                            navController.navigate(AppScreen.SignUpInfo.name)
                                      },
                        onConfirmClick = {
                            errorMessage = null
                            if (newPwd != confirmPwd) {
                                errorMessage = "Passwords do not match"
                            }
                            else {

                            }

                        }
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
                //Doctor Profile
                composable(route = AppScreen.DocProfile.name) {
                    ProfileScreen(
                        viewModel = accViewModel,
                        onBackClick = {},
                        onEditClick = {},
                        onLogoutClick = {}
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
                            .fillMaxHeight()
                    )
                }
            }


        }
    }
}

