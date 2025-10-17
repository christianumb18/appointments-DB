package com.example.agendas.data.model

data class Appointment(
    val appointmentID: Long? = null,
    val date: String,
    val subject: String,
    val activity: String
)