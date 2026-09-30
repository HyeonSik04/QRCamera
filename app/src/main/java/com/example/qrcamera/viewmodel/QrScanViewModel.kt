package com.example.qrcamera.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ScanState {
    object Idle : ScanState()
    object Scanning : ScanState()
    data class Matched(val identifier: String) : ScanState()
    object Mismatched : ScanState()
}

class QrScanViewModel : ViewModel() {

    private val _scanState = MutableStateFlow<ScanState>(ScanState.Idle)
    val scanState: StateFlow<ScanState> = _scanState.asStateFlow()

    // SYS-REQ-002-001, 002: 저장된 데이터 5개 (QR 값 → 식별자)
    private val storedQrDataMap: Map<String, String> = mapOf(
        "QR_CARD_001" to "ID_A001",
        "QR_CARD_002" to "ID_A002",
        "QR_CARD_003" to "ID_A003",
        "QR_CARD_004" to "ID_A004",
        "QR_CARD_005" to "ID_A005"
    )

    // 가장 최근에 인식된 식별자를 저장하는 변수
    var lastIdentifier: String? = null
        private set

    private var isProcessing = false

    fun startScan() {
        isProcessing = false
        _scanState.value = ScanState.Scanning
    }

    fun onQrDetected(value: String) {
        if (_scanState.value !is ScanState.Scanning || isProcessing) return
        isProcessing = true

        val identifier = storedQrDataMap[value]
        if (identifier != null) {
            lastIdentifier = identifier
            _scanState.value = ScanState.Matched(identifier)
        } else {
            _scanState.value = ScanState.Mismatched   // SYS-REQ-002-003
        }
    }

    fun retryScan() = startScan()
}