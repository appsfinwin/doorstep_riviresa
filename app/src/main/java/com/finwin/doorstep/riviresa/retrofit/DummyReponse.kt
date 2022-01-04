package com.finwin.doorstep.riviresa.retrofit

data class DummyReponse(
    val receipt: Receipt
)

data class Receipt(
    val error: Error
)

data class Error(
    val msg: String
)