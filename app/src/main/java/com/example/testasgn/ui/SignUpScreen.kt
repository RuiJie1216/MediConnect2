package com.example.testasgn.ui

import com.example.testasgn.R
import android.content.res.Configuration
import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testasgn.ui.theme.ArimaTypography
import com.example.testasgn.ui.theme.TestAsgnTheme

@Composable
fun SignUpInfoScreen(
    modifier: Modifier = Modifier,
    existPatient: Boolean?,
    onChangeExistPatient: (Boolean?) -> Unit,
    ic: String,
    onChangeIc: (String) -> Unit,
    name: String,
    onChangeName: (String) -> Unit,
    email: String,
    onChangeEmail: (String) -> Unit,
    phone: String,
    onChangePhone: (String) -> Unit,
    read: Boolean,
    onChangeRead: (Boolean) -> Unit,
    errorMessage: String?,
    onNextClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(if(isSystemInDarkTheme()) Color.DarkGray else Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About Me",
                color = if(isSystemInDarkTheme()) Color.LightGray else Color.Black
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
        ) {
            Text(
                text = "Are you our existing patient?",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditExistPatient(
                existPatient = existPatient,
                onChangeExistPatient = onChangeExistPatient
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = stringResource(R.string.ic_passport),
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditIcTextField(
                ic = ic,
                onChangeIc = onChangeIc,
                errorMessage = errorMessage
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = "Full Name",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditNameTextField(
                name = name,
                onChangeName = onChangeName
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = "Email",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditEmailTextField(
                email = email,
                onChangeEmail = onChangeEmail,
                errorMessage = errorMessage
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = "Phone Number",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditPhoneTextField(
                phone = phone,
                onChangePhone = onChangePhone,
                errorMessage = errorMessage
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = "I have read and agree to the Terms & Conditions",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditRead(
                read = read,
                onChangeRead = onChangeRead
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Terms & Conditions",
                    color = if (isSystemInDarkTheme()) Color(0xFF6F97F9) else Color.Blue,
                    fontSize = 13.sp

                )

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                NextButton(
                    existPatient = existPatient,
                    ic = ic,
                    name = name,
                    email = email,
                    phone = phone,
                    read = read,
                    onClick = onNextClick
                )
            }


        }

    }
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    existPatient: Boolean?,
    ic: String,
    name: String,
    email: String,
    phone: String,
    read: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(200.dp),
        enabled = existPatient != null && ic.isNotBlank() && name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && read,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Color(0xFF089A2D) else Color(0xFF34C759),
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Confirm"
        )
    }
}

@Composable
fun EditRead(
    modifier: Modifier = Modifier,
    read: Boolean,
    onChangeRead: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = read,
            onClick = { onChangeRead(true) }
        )
        Text(
            text = "Yes",
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

@Composable
fun EditPhoneTextField(
    modifier: Modifier = Modifier,
    phone: String,
    errorMessage: String?,
    onChangePhone: (String) -> Unit
) {
    TextField(
        value = phone,
        onValueChange = onChangePhone,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        singleLine = true,
        isError = errorMessage?.contains("Phone") == true,
        supportingText = {
            if (errorMessage?.contains("Phone") == true) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

@Composable
fun EditEmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    errorMessage: String?,
    onChangeEmail: (String) -> Unit
) {
    TextField(
        value = email,
        onValueChange = onChangeEmail,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        singleLine = true,
        isError = errorMessage?.contains("Email") == true,
        supportingText = {
            if (errorMessage?.contains("Email") == true) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}


@Composable
fun EditNameTextField(
    modifier: Modifier = Modifier,
    name: String,
    onChangeName: (String) -> Unit
) {
    TextField(
        value = name,
        onValueChange = onChangeName,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun EditIcTextField(
    modifier: Modifier = Modifier,
    ic: String,
    errorMessage: String?,
    onChangeIc: (String) -> Unit
) {
    TextField(
        value = ic,
        onValueChange = onChangeIc,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        singleLine = true,
        isError = errorMessage?.contains("IC") == true,
        supportingText = {
            if (errorMessage?.contains("IC") == true) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun EditExistPatient(
    modifier: Modifier = Modifier,
    existPatient: Boolean?,
    onChangeExistPatient: (Boolean?) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = (existPatient == true),
            onClick = { onChangeExistPatient(true) }
        )
        Text(
            text = "Yes",
            color = if (isSystemInDarkTheme()) Color.White else Color.Black

        )

        Spacer(
            modifier = Modifier
                .width(20.dp)
        )

        RadioButton(
            selected = (existPatient == false),
            onClick = {onChangeExistPatient(false)}
        )
        Text(
            text = "No",
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

@Composable
fun SignUpPwdScreen(
    modifier: Modifier = Modifier,
    newPwd: String,
    onChangeNewPwd: (String) -> Unit,
    confirmPwd: String,
    onChangeConfirmPwd: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    errorMessage: String?
) {
    var calcProgress by remember(newPwd) { mutableFloatStateOf(0f) }

    val conditionCheck = listOf(
        newPwd.length >= 6,
        newPwd.any { it.isUpperCase() },
        newPwd.any { it.isLowerCase() },
        newPwd.any { it.isDigit() }
    )

    calcProgress = conditionCheck.count{ it } / 4f
    val safeProgress = calcProgress.coerceIn(0f, 1f)

    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(if(isSystemInDarkTheme()) Color.DarkGray else Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Set Password",
                color = if(isSystemInDarkTheme()) Color.LightGray else Color.Black
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
        ) {
            Text(
                text = "Password",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )
            
            EditNewPwdTextField(
                newPwd = newPwd,
                onChangePwd = onChangeNewPwd
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            CheckPwdFormat(
                safeProgress = safeProgress,
                conditionCheck = conditionCheck,
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
            )

            Text(
                text = "Confirm Password",
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )

            EditConfirmPwdTextField(
                confirmPwd = confirmPwd,
                onChangeConfirmPwd = onChangeConfirmPwd,
                errorMessage = errorMessage
            )

            Spacer(
                modifier = Modifier
                    .height(200.dp)
            )

            if (errorMessage != "Passwords do not match" && errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = ArimaTypography.displaySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    Text(
                        text = "Back"
                    )
                }

                Button(
                    onClick = onConfirmClick,
                    modifier = Modifier
                        .width(100.dp),
                    enabled = newPwd.isNotBlank() && confirmPwd.isNotBlank() && safeProgress > 0.75f,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) Color(0xFF089A2D) else Color(0xFF34C759),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Confirm"
                    )
                }
            }


        }
    }

}

@Composable
fun EditConfirmPwdTextField(
    modifier: Modifier = Modifier,
    confirmPwd: String,
    errorMessage: String?,
    onChangeConfirmPwd: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = confirmPwd,
        onValueChange = onChangeConfirmPwd,
        singleLine = true,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(8.dp),
        isError = errorMessage?.contains("Passwords do not match") == true,
        supportingText = {
            if (errorMessage?.contains("Passwords do not match") == true) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Icon(
                    imageVector = image,
                    contentDescription = description
                )
            }
        }
    )
}

@Composable
fun CheckPwdFormat(
    modifier: Modifier = Modifier,
    safeProgress: Float,
    conditionCheck: List<Boolean>
) {

    val color = when {
        safeProgress < 0.5f -> Color.Red
        safeProgress < 0.75f -> Color(0xFFFFA500)
        safeProgress < 1f -> Color.Yellow
        else -> Color.Green
    }

    Column(
        modifier = modifier
    ) {
        LinearProgressIndicator(
            progress = { safeProgress },
            color = color,
            trackColor = ProgressIndicatorDefaults.linearTrackColor,
            modifier = Modifier
                .padding(vertical = 5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "check 6 character",
                tint = if (!conditionCheck[0]) {
                    if (isSystemInDarkTheme()) Color.White else Color.Black
                } else {
                    Color.Green
                }
            )
            Text(
                text = "At least 6 characters",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "check one upper",
                tint = if (!conditionCheck[1]) {
                    if (isSystemInDarkTheme()) Color.White else Color.Black
                } else {
                    Color.Green
                }
            )
            Text(
                text = "At least one capital letter",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "check one lower",
                tint = if (!conditionCheck[2]) {
                    if (isSystemInDarkTheme()) Color.White else Color.Black
                } else {
                    Color.Green
                }
            )
            Text(
                text = "At least one small letter",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "check one digit",
                tint = if (!conditionCheck[3]) {
                    if (isSystemInDarkTheme()) Color.White else Color.Black
                } else {
                    Color.Green
                }
            )
            Text(
                text = "At least one digit",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

    }
}

@Composable
fun EditNewPwdTextField(
    newPwd: String,
    onChangePwd: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = newPwd,
        onValueChange = onChangePwd,
        singleLine = true,
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        }
    )
}

@Composable
fun SignUpSuccessScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500))
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color(0xFF4CAF50), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Check",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                )
            }
        }


        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(700, delayMillis = 200))
        ) {
            Text(
                text = "Sign Up Success",
                fontSize = 40.sp,
                style = ArimaTypography.displayMedium,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(900, delayMillis = 200))
        ) {
            Text(
                text = "You Can Login Now",
                fontSize = 20.sp,
                style = ArimaTypography.displaySmall,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 400))
        ) {
            Button(
                onClick = onBackClick
            ) {
                Text("Continue")
            }
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpSuccessScreenPreview() {
    TestAsgnTheme {
        SignUpSuccessScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPwdScreenPreview() {
    TestAsgnTheme {
        SignUpPwdScreen(
            newPwd = "",
            onChangeNewPwd = {},
            confirmPwd = "",
            onChangeConfirmPwd = {},
            onBackClick = {},
            onConfirmClick = {},
            errorMessage = null
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpPwdScreenPreviewDark() {
    TestAsgnTheme {
        SignUpPwdScreen(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            newPwd = "",
            onChangeNewPwd = {},
            confirmPwd = "",
            onChangeConfirmPwd = {},
            onBackClick = {},
            onConfirmClick = {},
            errorMessage = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpInfoScreenPreview() {
    TestAsgnTheme {
        SignUpInfoScreen(
            existPatient = null,
            onChangeExistPatient = {},
            ic = "",
            onChangeIc = {},
            name = "",
            onChangeName = {},
            email = "",
            onChangeEmail = {},
            phone = "",
            onChangePhone = {},
            read = false,
            errorMessage = null,
            onChangeRead = {},
            onNextClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpInfoScreenPreviewDark() {
    TestAsgnTheme {
        SignUpInfoScreen(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            existPatient = null,
            onChangeExistPatient = {},
            ic = "",
            onChangeIc = {},
            name = "",
            onChangeName = {},
            email = "",
            onChangeEmail = {},
            phone = "",
            onChangePhone = {},
            read = false,
            errorMessage = null,
            onChangeRead = {},
            onNextClick = {}
        )
    }
}