package com.example.prerpare.ui.mainView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.prerpare.ui.mainlist.MainListActivity
import com.example.prerpare.ui.theme.PrerpareTheme
import androidx.compose.runtime.collectAsState

class MainActivity : ComponentActivity() {

    private  val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent = Intent(this, MainListActivity::class.java)
        setContent {
            PrerpareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier
                            .clickable(
                                onClick = { startActivity(intent) },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = LocalIndication.current // 默认水波纹
                            )
                            .padding(innerPadding),
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,viewModel: MainViewModel) {
    var countNum by remember { mutableStateOf(0) }

    Column {
        Text(
            text = "Hello $name!$countNum", modifier = modifier, textAlign = TextAlign.Center

        )
        Row {
            Text(
                text = "test1 $name!", modifier = modifier
            )
            Text(
                text = "test2 $name!", modifier = modifier
            )
        }
        SearchBox(
            keyword=viewModel.keyWordState.collectAsState().value,
            onKeywordChanged=viewModel::onKeyWordChanged)

        Button(
            modifier = modifier, onClick = {
                println("点击了按钮")
                countNum++
            }) {
            Text(text = "点击我")
        }
    }

}

@Composable
fun SearchBox( keyword:String, onKeywordChanged:(String)-> Unit ){
    TextField(
        value = keyword,
        onValueChange = onKeywordChanged

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrerpareTheme {
        Greeting("Android", viewModel = MainViewModel())
    }
}