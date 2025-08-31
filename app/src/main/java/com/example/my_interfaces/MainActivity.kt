package com.example.my_interfaces

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.my_interfaces.ui.theme.MyInterfacesTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyInterfacesTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()){
                    NavHost(navController = navController, startDestination = "login"){
                        composable("login") {
                            LoginScreen(
                                onLogin = { userName ->
                                    navController.navigate("home/${userName.ifBlank { "Convidado" }}")
                                },
                                onRegisterClick = {
                                    navController.navigate("register")
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onRegisterComplete = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            "home/{userName}",
                            arguments = listOf(navArgument("userName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val userName =
                                backStackEntry.arguments?.getString("userName") ?: "Usuário"
                            HomeScreen(
                                userName = userName,
                                onCadastrarProduto = {
                                    navController.navigate("cadastrarProduto")
                                },
                                onListarProdutos = {
                                    navController.navigate("listarProdutos")
                                },
                                onLogout = {
                                    navController.popBackStack("login", inclusive = false)
                                }
                            )
                        }
                        composable("cadastrarProduto") {
                            CadastrarProdutoScreen(
                                onRegisterComplete = { navController.popBackStack() }
                            )
                        }
                        composable("listarProdutos") {
                            ListarProdutosScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

