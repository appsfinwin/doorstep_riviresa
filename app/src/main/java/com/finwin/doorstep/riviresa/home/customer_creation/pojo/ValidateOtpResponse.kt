package com.finwin.doorstep.riviresa.home.customer_creation.pojo

data class ValidateOtpResponse(
    val otp: OtpData
)

data class OtpData(
    val Msg: String,
    val status: String
)