package com.example.qrcamera

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.qrcamera.ui.theme.QrcameraTheme
import com.example.qrcamera.viewmodel.QrScanViewModel

class MainActivity : ComponentActivity() {

    // ★ ViewModel을 여기서 생성 (지난번 크래시 원인이던 viewModel() 함수 사용 안 함)
    private val qrViewModel: QrScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QrcameraTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QrScanScreen(
                        viewModel = qrViewModel,
                        onMatched = { identifier ->
                            // 인식된 식별자가 여기로 들어옴 (다음 단계 처리 위치)
                            Log.d("QR", "인식된 식별자: $identifier")
                        }
                    )
                }
            }
        }
    }
}