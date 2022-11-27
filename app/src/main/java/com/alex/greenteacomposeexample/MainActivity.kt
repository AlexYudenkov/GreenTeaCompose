package com.alex.greenteacomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alex.greenteacomposeexample.ui.theme.GreenTeaComposeExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenTeaComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreenTeaText()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreenTeaText(
    modifier: Modifier = Modifier,
) {
    val vm = remember {
        ViewModelGreenTea(dependencies = GreenTeaFeature.Dependencies())
    }

    val profileState = vm.state.collectAsState()
    profileState.value?.let {
        TextField(
            value = it.message,
            onValueChange = { string ->
                vm.dispatch(GreenTeaFeature.Message.TextUpdated(string))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreenTeaComposeExampleTheme {
        GreenTeaText()
    }
}