package com.example.agendas.data.model

import java.util.Date

data class Appointment(
    val appointmentID: Long? = null,
    val date: String,
    val subject: String,
    val activity: String,
    val showDate: Date?
)