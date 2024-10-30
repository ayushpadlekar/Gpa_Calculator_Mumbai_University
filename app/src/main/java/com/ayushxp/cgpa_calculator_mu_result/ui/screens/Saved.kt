package com.ayushxp.cgpa_calculator_mu_result.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ayushxp.cgpa_calculator_mu_result.BottomNavigationBar
import com.ayushxp.cgpa_calculator_mu_result.R
import com.ayushxp.cgpa_calculator_mu_result.TopBar
import com.ayushxp.cgpa_calculator_mu_result.data.model.GpaEntity
import com.ayushxp.cgpa_calculator_mu_result.ui.theme.CGPA_Calculator_MU_ResultTheme
import com.ayushxp.cgpa_calculator_mu_result.viewmodel.GpaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Saved {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun SavedScreen(viewModel: GpaViewModel = hiltViewModel()) {
        val savedGPAList by viewModel.allGPA.observeAsState(emptyList())
        val snackbarHostState = remember { SnackbarHostState() }
        var recentlyDeletedGpa: GpaEntity? by remember { mutableStateOf(null) }

        // Map to hold expanded states for each GPA item
        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {

            LazyColumn() {
                items(savedGPAList) { gpa ->

                    val isExpanded = expandedStates[gpa.id] ?: false

                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp).padding(top = 12.dp)
                            .clickable { expandedStates[gpa.id] = !isExpanded }, // Toggle expand state on click
                        colors = CardDefaults.outlinedCardColors(Color(0xFFECE0FF)),
                        shape = CardDefaults.outlinedShape,
                        border = BorderStroke(2.dp, Color(0xFF6100FF))
                    ) {

                        // Date & Expand Button
                        Row (verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 4.dp))
                        {

                            Text(gpa.dateTime)

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                onClick = { expandedStates[gpa.id] = !isExpanded },
                                modifier = Modifier
                                    .size(34.dp)
                            ) {
                                Icon(
                                    imageVector =
                                    if (isExpanded)
                                        ImageVector.vectorResource(id = R.drawable.baseline_expand_less_24)
                                    else ImageVector.vectorResource(id = R.drawable.baseline_expand_more_24),
                                    modifier = Modifier.size(30.dp),
                                    tint = Color(0xFF6100FF),
                                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                                )
                            }
                        }


                        // Semester details Conditional visibility
                        if (isExpanded) {

                            HorizontalDivider(
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                color = Color.LightGray,
                                thickness = 1.dp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // First row: Semesters 1 to 3
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                listOf("SEM 1" to gpa.sem1, "SEM 2" to gpa.sem2, "SEM 3" to gpa.sem3)
                                    .forEach { (label, score) ->

                                        Row (modifier = Modifier.weight(1f))
                                        {
                                            Text(
                                                text = "$label : ",
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                                fontWeight = FontWeight.Medium
                                            )

                                            Text(
                                                text = "${if (score == 0.0f) "0" else score ?: "-"}",
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.inter_bold)),
                                                fontWeight = FontWeight.Medium,
                                                color = if (score == 0.0f) Color.Gray else Color(0xFF6100FF)
                                            )
                                        }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Second row: Semesters 4 to 6
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                listOf("SEM 4" to gpa.sem4, "SEM 5" to gpa.sem5, "SEM 6" to gpa.sem6)
                                    .forEach { (label, score) ->

                                        Row (modifier = Modifier.weight(1f))
                                        {
                                            Text(
                                                text = "$label : ",
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                                fontWeight = FontWeight.Medium
                                            )

                                            Text(
                                                text = "${if (score == 0.0f) "0" else score ?: "-"}",
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.inter_bold)),
                                                fontWeight = FontWeight.Medium,
                                                color = if (score == 0.0f) Color.Gray else Color(0xFF6100FF)
                                            )
                                        }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Third row: Semesters 7 and 8
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                listOf("SEM 7" to gpa.sem7, "SEM 8" to gpa.sem8, "" to null)
                                    .forEach { (label, score) ->

                                        Row (modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start)
                                        {
                                            if (label.isNotEmpty()) {
                                                Text(
                                                    text = "$label : ",
                                                    fontSize = 14.sp,
                                                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                                    fontWeight = FontWeight.Medium
                                                )
                                                Text(
                                                    text = "${if (score == 0.0f) "0" else score ?: "-"}",
                                                    fontSize = 14.sp,
                                                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                                                    fontWeight = FontWeight.Medium,
                                                    color = if (score == 0.0f) Color.Gray else Color(0xFF6100FF)
                                                )
                                            }
                                        }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                color = Color.LightGray,
                                thickness = 1.dp
                            )
                        }


                        // CGPA & Delete Button
                        Column(modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp))
                        {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "CGPA :  ",
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                    fontWeight = FontWeight.Medium
                                )

                                Text(
                                    text = "${gpa.cgpa} | ${gpa.percent}%",
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.0.sp,
                                    color = Color(0xFF6100FF)
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                // Delete Button
                                TextButton(
                                    onClick = {
                                    recentlyDeletedGpa = gpa // Save reference to the deleted GPA
                                    viewModel.deleteGPA(gpa) // Delete the GPA

                                    // Show Snackbar with Undo option
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val result = snackbarHostState.showSnackbar(
                                            message = "Deleted Successfully",
                                            actionLabel = "Undo",
                                            withDismissAction = true,
                                            duration = SnackbarDuration.Short
                                        )

                                        // Check if the action was pressed
                                        if (result == SnackbarResult.ActionPerformed) {
                                            recentlyDeletedGpa?.let {
                                                viewModel.saveGPA(
                                                    sem1 = it.sem1,
                                                    sem2 = it.sem2,
                                                    sem3 = it.sem3,
                                                    sem4 = it.sem4,
                                                    sem5 = it.sem5,
                                                    sem6 = it.sem6,
                                                    sem7 = it.sem7,
                                                    sem8 = it.sem8,
                                                    cgpa = it.cgpa,
                                                    percent = it.percent,
                                                    dateTime = it.dateTime, // Pass the original time
                                                    id = it.id // Pass the original ID
                                                )
                                            }

                                        }
                                        recentlyDeletedGpa = null // Reset after handling
                                    }
                                })
                                {
                                    Icon(
                                        imageVector = ImageVector
                                            .vectorResource(id = R.drawable.baseline_delete_forever_24),
                                        modifier = Modifier.size(26.dp),
                                        tint = Color.Red,
                                        contentDescription = "Delete button"
                                    )

                                    Spacer(modifier = Modifier.padding(2.dp))

                                    Text(text = "Delete",
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Red)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items(5) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row() {

                            Text(
                                text = "CGPA :  ",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "7.48  |  74.8%",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.inter_bold)),
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.0.sp,
                                color = Color(0xFF6100FF)
                            )

                            Spacer(modifier = Modifier.padding(10.dp))
                        }

                        Text("25/10/2024 05:40 PM")
                        Spacer(modifier = Modifier.padding(4.dp))

                        // Delete button
                        Button(
                            onClick = { }
                        ) {
                            Text("Delete")
                        }

                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}