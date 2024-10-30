package com.ayushxp.cgpa_calculator_mu_result.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayushxp.cgpa_calculator_mu_result.R

class SGPA {

    @Composable
    fun SgpaScreen() {

        Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center)
        {
            Column (
                modifier = Modifier.offset(y = (-70).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.app_dev_illustration),
                    contentDescription = "App Under Development Image",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "SGPA Calculator",
                    color = Color(0xFF6100FF),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    letterSpacing = 1.0.sp,
                    softWrap = true
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "is still under development...",
                    color = Color(0xFF6100FF),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    softWrap = true
                )

            }
        }
    }


    // Preview
    @Preview
    @Composable
    fun SgpaScreenPreview() {
        SgpaScreen()
    }
}