package com.example.roamly.ui.view
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.roamly.R
import com.example.roamly.ui.viewmodel.AuthState
import com.example.roamly.ui.viewmodel.AuthViewModel
import com.example.roamly.ui.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen(navController: NavController,
                   viewModel: RegisterViewModel = hiltViewModel(),
                   authViewModel: AuthViewModel
) {

    val context = LocalContext.current
    val authState by authViewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        when(authState) {
            is AuthState.Authenticated -> {
                Toast.makeText(context, "Cuenta creada. Verifica tu correo", Toast.LENGTH_SHORT).show()
                viewModel.clearFields()
                authViewModel.resetAuthState()
                navController.navigate("login")
            }
            is AuthState.EmailVerificationSent -> {
                viewModel.clearFields()
                Toast.makeText(context, (authState as AuthState.EmailVerificationSent).message, Toast.LENGTH_SHORT).show()
                navController.navigate("login")
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                viewModel.onBirthdateChanged(selectedDate)
            },
            2000, 0, 1 // default date
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.finalroamlylogo_3),
                contentDescription = "Roamly Logo",
                modifier = Modifier.size(100.dp)
            )
            //Spacer(modifier = Modifier.height(20.dp))

            // Nombre Completo
            OutlinedTextField(
                value = viewModel.storeName,
                onValueChange = { viewModel.onStoreNameChanged(it) },
                placeholder = { Text(stringResource(id = R.string.full_name), color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                isError = viewModel.storeNameError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            viewModel.storeNameError?.let {
                Text(text = it, color = Color.Red, fontSize = 12.sp)
            }
            //Spacer(modifier = Modifier.height(12.dp))

            // Email
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                placeholder = { Text(stringResource(id = R.string.email), color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(12.dp),
                isError = viewModel.emailError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            viewModel.emailError?.let {
                Text(it, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                placeholder = { Text(stringResource(id = R.string.password), color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                isError = viewModel.passwordError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            viewModel.passwordError?.let {
                    Text(it, color = Color.Red, fontSize = 12.sp)
            }
            //Spacer(modifier = Modifier.height(12.dp))

            // Confirmar Contraseña
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                placeholder = { Text(stringResource(id = R.string.confirm_password), color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(12.dp),
                isError = viewModel.confirmPasswordError != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            viewModel.confirmPasswordError?.let {
                Text(it, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))

            /*** Phone Number ***/
            CustomTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChanged(it) },
                placeholder = "Phone Number",
                keyboardType = KeyboardType.Phone
            )

            /*** Address ***/
            CustomTextField(
                value = viewModel.address,
                onValueChange = { viewModel.onAddressChanged(it) },
                placeholder = "Address"
            )

            /*** Country ***/
            CustomTextField(
                value = viewModel.country,
                onValueChange = { viewModel.onCountryChanged(it) },
                placeholder = "Country"
            )

            //Spacer(modifier = Modifier.height(12.dp))

            /*** Birthdate Picker ***/
            Button(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(
                    text = if (viewModel.birthdate.isEmpty()) "Select Birthdate" else "Birthdate: ${viewModel.birthdate}",
                    color = Color.Black
                )            }

            Spacer(modifier = Modifier.height(12.dp))

            /*** Accept Emails Switch ***/
            Switch(
                checked = viewModel.acceptEmails,
                onCheckedChange = { viewModel.onAcceptEmailsChanged(it) }
            )
            Text(text = "Accept emails about news and updates", fontSize = 14.sp, color = Color.Gray)


            //Spacer(modifier = Modifier.height(20.dp))

            // Botón de Registro
            Button(
                onClick = {
                    val isValid = viewModel.validateAllFields()
                    if (isValid) {
                        authViewModel.signup(
                            viewModel.email,
                            viewModel.password,
                            username = viewModel.email.substringBefore("@"),
                            name = viewModel.storeName,
                            phoneNumber = viewModel.phoneNumber,
                            birthdate = viewModel.birthdate,
                            address = viewModel.address,
                            country = viewModel.country,
                            acceptEmails = viewModel.acceptEmails
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = stringResource(id = R.string.sign_up), color = Color.White, fontSize = 18.sp)
            }

            //Spacer(modifier = Modifier.height(10.dp))

            // Ir a Login
            TextButton(onClick = {
                viewModel.clearFields()
                navController.navigate("login")
            }) {
                Text(text = stringResource(id = R.string.already_have_account), color = Color.Gray)
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = error != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        error?.let {
            Text(text = it, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}