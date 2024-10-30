package com.ayushxp.cgpa_calculator_mu_result.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ayushxp.cgpa_calculator_mu_result.BottomNavigationBar
import com.ayushxp.cgpa_calculator_mu_result.R
import com.ayushxp.cgpa_calculator_mu_result.TopBar
import com.ayushxp.cgpa_calculator_mu_result.ui.theme.CGPA_Calculator_MU_ResultTheme
import com.ayushxp.cgpa_calculator_mu_result.viewmodel.GpaViewModel

class CGPA {
    @Composable
    fun CgpaScreen(viewModel: GpaViewModel = hiltViewModel()) {
        val semesters = viewModel.semesters
        val cgpa by viewModel.cgpa.observeAsState()
        val percent by viewModel.percent.observeAsState()

        // Regex pattern allowing numbers like 5., 5.5, 5.55, or 10.00
        val decimalPattern = Regex("^(10(\\.0{0,2})?|[1-9](\\.[0-9]{0,2})?)?$")

        // Focus Manager to clear focus on tap outside
        val focusManager = LocalFocusManager.current

        var focusedTextFieldIndex by remember { mutableStateOf(-1) } // Track focused text field


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        if (focusedTextFieldIndex >= 0) {
                            focusManager.clearFocus()
                            focusedTextFieldIndex = -1 // Reset the focused index
                        }
                    })
                }.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            var isVisible by remember { mutableStateOf(false) }

            Spacer(modifier = Modifier.height(24.dp))

            // First Row (3 text fields)
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                for (i in 1..3) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(80.dp)
                            .onFocusChanged { focusState ->
                                focusedTextFieldIndex =
                                    if (focusState.isFocused) i else -1 // Update the focused index
                            },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF6100FF),
                            focusedBorderColor = Color(0xFF6100FF),
                            focusedTextColor = Color(0xFF6100FF),
                            focusedLabelColor = Color(0xFF6100FF)
                        ),
                        shape = ShapeDefaults.Small,
                        value = semesters[i - 1],
                        onValueChange = { input ->
                            if (input.isEmpty()) {
                                viewModel.updateSemester(i - 1, "")
                            } else if (input.matches(decimalPattern)) {
                                viewModel.updateSemester(i - 1, input)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        label = { Text("Sem $i", textAlign = TextAlign.Center, fontSize = 12.sp) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Second Row (3 text fields)
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                for (i in 4..6) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(80.dp)
                            .onFocusChanged { focusState ->
                                focusedTextFieldIndex =
                                    if (focusState.isFocused) i else -1 // Update the focused index
                            },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF6100FF),
                            focusedBorderColor = Color(0xFF6100FF),
                            focusedTextColor = Color(0xFF6100FF),
                            focusedLabelColor = Color(0xFF6100FF)
                        ),
                        shape = ShapeDefaults.Small,
                        value = semesters[i - 1],
                        onValueChange = { input ->
                            if (input.isEmpty()) {
                                viewModel.updateSemester(i - 1, "")
                            } else if (input.matches(decimalPattern)) {
                                viewModel.updateSemester(i - 1, input)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        label = { Text("Sem $i", textAlign = TextAlign.Center, fontSize = 12.sp) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Third Row (2 text fields & Reset button)
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 7..8) {
                    OutlinedTextField(
                        modifier = Modifier
                            .width(80.dp)
                            .onFocusChanged { focusState ->
                                focusedTextFieldIndex =
                                    if (focusState.isFocused) i else -1 // Update the focused index
                            },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF6100FF),
                            focusedBorderColor = Color(0xFF6100FF),
                            focusedTextColor = Color(0xFF6100FF),
                            focusedLabelColor = Color(0xFF6100FF)
                        ),
                        shape = ShapeDefaults.Small,
                        value = semesters[i - 1],
                        onValueChange = { input ->
                            if (input.isEmpty()) {
                                viewModel.updateSemester(i - 1, "")
                            } else if (input.matches(decimalPattern)) {
                                viewModel.updateSemester(i - 1, input)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        label = { Text("Sem $i", textAlign = TextAlign.Center, fontSize = 12.sp) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }

                // Reset Button
                Image(
                    painter = painterResource(id = R.drawable.reset_button),
                    contentDescription = "reset button",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(width = 80.dp, height = 55.dp)
                        .offset(y = 4.dp)
                        .clickable(onClick = {
                            viewModel.reset()
                        })
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Calculate & See Graph Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {

                // Calculate Button
                Button(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF8D48FF),
                                    Color(0xFF6100FF)
                                )
                            ), shape = ButtonDefaults.shape
                        )
                        .height(ButtonDefaults.MinHeight),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    onClick = {
                        val cgpaInput = semesters.map { it?.toFloatOrNull() ?: 0f }

                        viewModel.calculateCGPA(cgpaInput)
                        viewModel.calculatePercentage(cgpaInput)
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.calculator_icon),
                            contentDescription = "Calculator  icon",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Calculate", color = Color.White, fontSize = 18.sp)
                    }
                }

                // See Graph Button
                Button(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF8D48FF),
                                    Color(0xFF6100FF)
                                )
                            ), shape = ButtonDefaults.shape
                        )
                        .height(ButtonDefaults.MinHeight),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    onClick = { isVisible = !isVisible }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.pie_chart_icon),
                            contentDescription = "See Graph icon",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        if(!isVisible) {
                            Text("See Graph", color = Color.White, fontSize = 18.sp)
                        } else {
                            Text("Hide Graph", color = Color.White, fontSize = 18.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Display CGPA result & Save button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                // display cgpa & percent together
                Text(
                    "CGPA :", fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semibold)),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(8.dp))

                cgpa?.let { cgpa ->
                    Text(
                        "$cgpa",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.inter_bold)),
                        letterSpacing = 1.0.sp,
                        color = Color(0xFF6100FF)
                    )

                    percent?.let { percent ->

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "|", fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        // Format percentage to remove trailing .0 if it's a whole number
                        val formattedPercent = if (percent % 1 == 0f) {
                            percent.toInt()
                                .toString()  // If the percentage is whole, convert to Int and remove .0
                        } else {
                            percent.toString()  // Otherwise, keep it as a float with decimal places
                        }

                        Text(
                            "$formattedPercent%",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(Font(R.font.inter_bold)),
                            letterSpacing = 1.0.sp,
                            color = Color(0xFF6100FF)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Save Button (Enabled after CGPA is calculated)
                Button(
                    onClick = {
                        val cgpaInput = semesters.map { it?.toFloatOrNull() ?: 0f }
                        viewModel.saveGPA(
                            cgpaInput[0], cgpaInput[1], cgpaInput[2], cgpaInput[3],
                            cgpaInput[4], cgpaInput[5], cgpaInput[6], cgpaInput[7],
                            cgpa ?: 0f, percent ?: 0f
                        )
                        // Toast of Saved Successfully
                    },
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF8D48FF),
                                    Color(0xFF6100FF)
                                )
                            ),
                            shape = ButtonDefaults.shape // Ensure the shape is consistent
                        ).height(ButtonDefaults.MinHeight),
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray
                        ),
                    enabled = cgpa != null
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.save_icon),
                            contentDescription = "save  icon",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Save", color = Color.White, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Bar Graph Chart
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn() + expandVertically(tween(500)),
                exit = fadeOut() + shrinkVertically(tween(500))
            )
            {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(270.dp)
                        .scrollable(state = rememberScrollState(), orientation = Orientation.Horizontal)
                        .padding(horizontal = 16.dp).padding(bottom = 16.dp),
                    shape = CardDefaults.outlinedShape,
                    colors = CardDefaults.outlinedCardColors(Color(0xFFECE0FF)),
                    border = BorderStroke(0.5.dp, Color(0xFF6100FF))
                ) {
                    BarChart(
                        semesterSgpa = semesters.map { it.toFloatOrNull() ?: 0f }, // List of SGPA values
                        modifier = Modifier.padding(1.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }


            // Note Card
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(horizontal = 16.dp).padding(bottom = 16.dp),
                    shape = CardDefaults.outlinedShape,
                    colors = CardDefaults.outlinedCardColors(Color(0xFFECE0FF)),
                    border = BorderStroke(0.5.dp, Color(0xFF6100FF))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val bulletPoint = "• "
                        val textList = listOf(
                            "Enter SGPA of the semesters.",
                            "Input between 1.00 to 10.00 only.",
                            "Leave empty if you don't have any SGPA yet."
                        )

                        textList.forEach { text ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = bulletPoint,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                                    color = Color.Black
                                )
                                Text(
                                    text = text,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 4.dp) // Adjust padding here for space between bullet and text
                                        .weight(1f) // Allow text to take remaining space
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Formula for CGPA :",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.inter_semibold)),
                            color = Color.Black
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Sum of SGPA of All Semesters",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            color = Color.Black
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            thickness = 1.5.dp,
                            color = Color.Black
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Total Number of Semesters",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            color = Color.Black
                        )
                    }
                }

        }
    }
}


// Bar Graph Chart Composable
@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    semesterSgpa: List<Float>,
    maxYValue: Float = 10f,
    labelColor: Color = Color.Black,
    gridLineColor: Color = Color.DarkGray
) {
    val numberOfSemesters = semesterSgpa.size
    val xAxisLabels = listOf("Sem1", "Sem2", "Sem3", "Sem4", "Sem5", "Sem6", "Sem7", "Sem8")

    Canvas(modifier = modifier.fillMaxSize().padding(top = 8.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Independent padding values
        val yAxisLabelPadding = 40.dp.toPx()
        val xAxisLabelPadding = 20.dp.toPx()
        val topPadding = 20.dp.toPx()
        val bottomPadding = 40.dp.toPx()

        // Calculating available drawing area after padding
        val drawableWidth = canvasWidth - xAxisLabelPadding
        val drawableHeight = canvasHeight - topPadding - bottomPadding

        // Spacing between each bar
        val barSpacing = drawableWidth / (numberOfSemesters + 1)

        // Draw Y-axis line (vertical line)
        drawLine(
            color = Color.Black,
            start = Offset(yAxisLabelPadding, topPadding - 5.dp.toPx()),
            end = Offset(yAxisLabelPadding, canvasHeight - bottomPadding),
            strokeWidth = 2f
        )

        // Draw X-axis line (horizontal line at 0.00)
        drawLine(
            color = Color.Black,
            start = Offset(yAxisLabelPadding, drawableHeight + topPadding),
            end = Offset(canvasWidth - 15.dp.toPx(), drawableHeight + topPadding),
            strokeWidth = 2f
        )

        // Drawing horizontal grid lines for Y-axis
        for (i in 0..10) {
            val y = drawableHeight - (drawableHeight / maxYValue) * i + topPadding
            drawLine(
                color = gridLineColor,
                start = Offset(yAxisLabelPadding, y),
                end = Offset(canvasWidth - 15.dp.toPx(), y),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            // Y-axis labels (0.00 to 10.00)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    String.format("%.2f", i.toFloat()),
                    yAxisLabelPadding / 2,
                    y,
                    android.graphics.Paint().apply {
                        color = labelColor.toArgb()
                        textSize = 26f
                        typeface = android.graphics.Typeface.DEFAULT_BOLD
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }

        // Drawing Bars
        for (i in semesterSgpa.indices) {

                val x = 20.dp.toPx() + (i + 1) * barSpacing
                val barHeight = (drawableHeight / maxYValue) * semesterSgpa[i]

            if (semesterSgpa[i] > 0f) { // Only draw the bar if SGPA is greater than 0
                // Bar
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8D48FF),
                            Color(0xFF6100FF)
                        )
                    ),
                    topLeft = Offset(x - barSpacing / 4, drawableHeight - barHeight + topPadding),
                    size = Size(barSpacing / 2, barHeight)
                )

                // Bar Value at top of each bar
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        String.format("%.2f", semesterSgpa[i]),
                        x,
                        drawableHeight - barHeight + topPadding - 10,
                        android.graphics.Paint().apply {
                            color = labelColor.toArgb()
                            textSize = 26f
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
            }

                // X-axis labels
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        xAxisLabels[i],
                        x,
                        canvasHeight - xAxisLabelPadding,
                        android.graphics.Paint().apply {
                            color = labelColor.toArgb()
                            textSize = 28f
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MergedCgpaScreenPreview() {
    // Mock data for the preview
    val mockSemesters =
        remember { mutableStateListOf<Float?>(8.0f, 7.5f, 9.0f, null, null, null, null, null) }
    val mockCgpa = 8.2f
    val mockPercent = 82.0f

    CGPA_Calculator_MU_ResultTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(title = "GPA CALCULATOR", subtitle = "for Mumbai University Results")
            },
            bottomBar = {
                BottomNavigationBar(navController = rememberNavController())
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                var isVisible by remember { mutableStateOf(true) }

                Spacer(modifier = Modifier.height(24.dp))

                // First Row (3 text fields)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    for (i in 1..3) {
                        OutlinedTextField(
                            modifier = Modifier.width(80.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF6100FF),
                                focusedBorderColor = Color(0xFF6100FF),
                                focusedTextColor = Color(0xFF6100FF),
                                focusedLabelColor = Color(0xFF6100FF)
                            ),
                            shape = ShapeDefaults.Small,
                            value = mockSemesters[i - 1]?.toString() ?: "",
                            onValueChange = { /* No-op in preview */ },
                            textStyle = TextStyle(textAlign = TextAlign.Center),
                            label = {
                                Text(
                                    "Sem $i",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Second Row (3 text fields)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    for (i in 4..6) {
                        OutlinedTextField(
                            modifier = Modifier.width(80.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF6100FF),
                                focusedBorderColor = Color(0xFF6100FF),
                                focusedTextColor = Color(0xFF6100FF),
                                focusedLabelColor = Color(0xFF6100FF)
                            ),
                            shape = ShapeDefaults.Small,
                            value = mockSemesters[i - 1]?.toString() ?: "",
                            onValueChange = { /* No-op in preview */ },
                            textStyle = TextStyle(textAlign = TextAlign.Center),
                            label = {
                                Text(
                                    "Sem $i",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Third Row (2 text fields & Reset Button)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 7..8) {
                        OutlinedTextField(
                            modifier = Modifier.width(80.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF6100FF),
                                focusedBorderColor = Color(0xFF6100FF),
                                focusedTextColor = Color(0xFF6100FF),
                                focusedLabelColor = Color(0xFF6100FF)
                            ),
                            shape = ShapeDefaults.Small,
                            value = mockSemesters[i - 1]?.toString() ?: "",
                            onValueChange = { /* No-op in preview */ },
                            textStyle = TextStyle(textAlign = TextAlign.Center),
                            label = {
                                Text(
                                    "Sem $i",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.reset_button),
                        contentDescription = "reset button",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(width = 80.dp, height = 55.dp)
                            .offset(y = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Calculate & See Graph Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Button(
                        onClick = { /* No-op in preview */ },
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF8D48FF),
                                        Color(0xFF6100FF)
                                    )
                                ), shape = ButtonDefaults.shape
                            )
                            .height(ButtonDefaults.MinHeight),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.calculator_icon),
                                contentDescription = "calculator  icon",
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Calculate", color = Color.White, fontSize = 18.sp)
                        }
                    }

                    Button(
                        onClick = { isVisible = !isVisible },
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF8D48FF),
                                        Color(0xFF6100FF)
                                    )
                                ), shape = ButtonDefaults.shape
                            )
                            .height(ButtonDefaults.MinHeight),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.pie_chart_icon),
                                contentDescription = "see graph  icon",
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("See Graph", color = Color.White, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Display CGPA result & Save button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("CGPA : $mockCgpa", fontSize = 20.sp)

                    Spacer(modifier = Modifier.width(10.dp))

                    Text("|", fontSize = 20.sp)

                    Spacer(modifier = Modifier.width(10.dp))

                    Text("$mockPercent%", fontSize = 20.sp)

                    Spacer(modifier = Modifier.width(16.dp))

                    // Save Button (Mocked behavior)
                    Button(
                        onClick = { /* No-op in preview */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6100FF),
                            disabledContainerColor = Color.Gray
                        ),
                        enabled = false
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.save_icon),
                                contentDescription = "calculator  icon",
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text("Save", color = Color.White, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                //Bar Graph Chart
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn() + expandVertically(tween(500)),
                    exit = fadeOut() + shrinkVertically(tween(500))
                )
                {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp).padding(bottom = 16.dp),
                        shape = CardDefaults.outlinedShape,
                        colors = CardDefaults.outlinedCardColors(Color(0xFFEBD9FF)),
                        border = BorderStroke(0.5.dp, Color(0xFF6100FF))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {

                            Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                // Define the chart padding, bar width, and spacing
                                val chartPadding = 20.dp.toPx()
                                val barWidth = 30.dp.toPx()
                                val barSpacing = 10.dp.toPx()
                                val maxBarHeight = size.height - chartPadding * 2
                                val maxYValue = 10f  // Maximum value on Y-axis (10.00)

                                // Draw Y-axis labels and dotted lines
                                for (i in 0..10) {
                                    val yPos = chartPadding + i * (maxBarHeight / maxYValue)
                                    drawContext.canvas.nativeCanvas.apply {
                                        drawText(
                                            "${10 - i}.00",  // Draw labels in reverse order (from 10.00 at top to 0.00 at bottom)
                                            0f,
                                            yPos,
                                            android.graphics.Paint().apply {
                                                color = android.graphics.Color.BLACK
                                                textSize = 30f
                                                textAlign = android.graphics.Paint.Align.CENTER
                                            }
                                        )
                                    }
                                    drawLine(
                                        color = Color.DarkGray,
                                        start = Offset(chartPadding, yPos),
                                        end = Offset(size.width - chartPadding, yPos),
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                    )
                                }

                                // Draw X-axis labels and bars for each semester
                                val semesters = listOf("Sem1", "Sem2", "Sem3", "Sem4", "Sem5", "Sem6", "Sem7", "Sem8")
                                val sgpaValues = semesters.mapIndexed { index, _ ->
                                    semesters[index].toFloatOrNull() ?: 0f  // Get SGPA values or default to 0
                                }

                                sgpaValues.forEachIndexed { index, sgpa ->
                                    // Calculate the bar height based on the SGPA value
                                    val barHeight = (sgpa / maxYValue) * maxBarHeight

                                    // Calculate X position for the bar and label
                                    val xPos = chartPadding + index * (barWidth + barSpacing)

                                    // Draw the bar
                                    drawRect(
                                        color = Color(0xFF6100FF),
                                        topLeft = Offset(xPos, size.height - chartPadding - barHeight),
                                        size = Size(barWidth, barHeight)
                                    )

                                    // Draw the SGPA value on top of the bar
                                    drawContext.canvas.nativeCanvas.apply {
                                        drawText(
                                            String.format("%.2f", sgpa),  // Format SGPA value to 2 decimal places
                                            xPos + barWidth / 2,
                                            size.height - chartPadding - barHeight - 10,  // Position text above the bar
                                            android.graphics.Paint().apply {
                                                color = android.graphics.Color.BLACK
                                                textSize = 30f
                                                textAlign = android.graphics.Paint.Align.CENTER
                                            }
                                        )
                                    }

                                    // Draw the semester label below the bar
                                    drawContext.canvas.nativeCanvas.apply {
                                        drawText(
                                            semesters[index],
                                            xPos + barWidth / 2,
                                            size.height - chartPadding / 2,  // Position label below the bar
                                            android.graphics.Paint().apply {
                                                color = android.graphics.Color.BLACK
                                                textSize = 30f
                                                textAlign = android.graphics.Paint.Align.CENTER
                                            }
                                        )
                                    }
                                }
                            }

                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Note Card
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(horizontal = 16.dp).padding(bottom = 16.dp),
                    shape = CardDefaults.outlinedShape,
                    colors = CardDefaults.outlinedCardColors(Color(0xFFEBD9FF)),
                    border = BorderStroke(0.5.dp, Color(0xFF6100FF))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        val bulletPoint = "• "
                        val textList = listOf(
                            "Enter SGPA of the semesters.",
                            "Input between 1.00 to 10.00 only.",
                            "Leave empty if you don't have any SGPA yet."
                        )

                        textList.forEach { text ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text(
                                    text = bulletPoint,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                                    color = Color.Black
                                )
                                Text(
                                    text = text,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(start = 4.dp) // Adjust padding here for space between bullet and text
                                        .weight(1f) // Allow text to take remaining space
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Formula for CGPA :",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.inter_semibold)),
                            color = Color.Black
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Sum of SGPA of All Semesters",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            color = Color.Black
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 40.dp),
                            thickness = 1.5.dp,
                            color = Color.Black
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Total Number of Semesters",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            color = Color.Black
                        )
                    }
                }

            }
        }
    }
}

