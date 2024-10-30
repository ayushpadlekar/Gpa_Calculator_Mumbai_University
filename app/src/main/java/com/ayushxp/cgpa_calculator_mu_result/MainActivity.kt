package com.ayushxp.cgpa_calculator_mu_result

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ayushxp.cgpa_calculator_mu_result.ui.screens.CGPA
import com.ayushxp.cgpa_calculator_mu_result.ui.screens.SGPA
import com.ayushxp.cgpa_calculator_mu_result.ui.screens.Saved
import com.ayushxp.cgpa_calculator_mu_result.ui.theme.CGPA_Calculator_MU_ResultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(getColor(R.color.purple_500), getColor(R.color.purple_500)))

        setContent {
            CGPA_Calculator_MU_ResultTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize().padding(WindowInsets.statusBars.asPaddingValues()),
                    topBar = {
                        TopBar(title = "GPA CALCULATOR", subtitle = "for Mumbai University Results")
                    },
                    bottomBar = {
                        BottomNavigationBar(navController)
                })
                { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize().padding(paddingValues)
                    ) {
                        NavHost(navController = navController, startDestination = "cgpa") {
                            composable("cgpa") {
                                //CGPA Screen
                                CGPA().CgpaScreen()
                            }
                            composable("sgpa") {
                                //SGPA Screen
                                SGPA().SgpaScreen()
                            }
                            composable("saved") {
                                //Saved Screen
                                Saved().SavedScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}



//TOP BAR
@Composable
fun TopBar(title: String, subtitle: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
        color = Color.Transparent // Make the Surface color transparent
    ) {
        // Create a Box to layer the gradient and top bar content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF8D48FF), Color(0xFF6100FF)),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
        ) {
            
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 16.dp)
                    .align(Alignment.CenterStart)
            ) {
                // Title Text
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    fontSize = 24.sp,
                    letterSpacing = 1.5.sp,
                    style = TextStyle(
                        shadow = Shadow( // Apply shadow effect
                            color = Color(0xFF6100FF).copy(alpha = 0.3f),
                            offset = Offset(0f, 0f),
                            blurRadius = 10f
                        )
                    ),
                    modifier = Modifier.align(Alignment.Start) // Align title to the left
                )
                // Subtitle Text
                Text(
                    text = subtitle,
                    color = Color.White.copy(alpha = 0.8f), // Slightly transparent white
                    fontFamily = FontFamily(Font(R.font.inter_bold_italic)),
                    fontSize = 16.sp,
                    letterSpacing = 1.0.sp,
                    style = TextStyle(
                        shadow = Shadow( // Apply shadow effect
                            color = Color(0xFF6100FF).copy(alpha = 0.3f),
                            offset = Offset(0f, 0f),
                            blurRadius = 10f
                        )
                    ),
                    modifier = Modifier.align(Alignment.End).padding(start = 0.dp, top = 4.dp, end = 2.dp, bottom = 0.dp) // Align subtitle to the right
                )
            }

            Image(painter = painterResource(id = R.drawable.equation_icon),
                contentDescription = "equation icon",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(width = 90.dp, height = 35.dp).offset(x = -18.dp, y = 10.dp)
                    .align(Alignment.TopEnd) // Align logo to the right
            )
        }

    }
}



//BOTTOM NAVIGATION BAR
@Composable
fun BottomNavigationBar(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Color.Transparent // Make the Surface color transparent
    ) {
        // Create a Box to layer the gradient and NavigationBar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFF8D48FF), Color(0xFF6100FF)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
        ) {
            NavigationBar(
                containerColor = Color.Transparent // Keep the container color transparent
            ) {
                // Current route
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                // List of items
                val items = listOf(
                    BottomNavItems.CGPA,
                    BottomNavItems.SGPA,
                    BottomNavItems.Saved
                )

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { startRoute ->
                                    popUpTo(startRoute) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(end = 6.dp) // Optional padding
                                )
                                Text(text = item.title, color = Color.White, fontSize = 18.sp)
                            }
                        },
                        icon = {
                            // Leaving icon empty as it's included in the label.
                            Box {}
                        }
                    )
                }
            }
        }
    }
}


sealed class BottomNavItems(val route: String, val title: String, val icon: Int) {
    object CGPA : BottomNavItems("cgpa", "CGPA", icon = R.drawable.cgpa_icon)
    object SGPA : BottomNavItems("sgpa", "SGPA", icon = R.drawable.sgpa_icon)
    object Saved : BottomNavItems("saved", "Saved", icon = R.drawable.saved_icon)
}


//PREVIEW
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun TopAndBottomBarPreview() {
    CGPA_Calculator_MU_ResultTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(title = "GPA CALCULATOR", subtitle = "for Mumbai University Results")
            },
            bottomBar = {
                BottomNavigationBar(navController = rememberNavController())
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.White)
            )
        }
    }
}
