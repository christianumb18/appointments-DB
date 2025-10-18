package com.example.agendas.data.remote

import com.example.agendas.data.model.Appointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentApiService {
    @GET("/api/appointments")
    suspend fun getAppointments(): Response<List<Appointment>>

    @POST("/api/appointments")
    suspend fun addAppointment(@Body agenda: Appointment): Response<Void>

    @DELETE("/api/appointments/{id}")
    suspend fun deleteAppointment(@Path("id") id: Long): Response<Void>

    @DELETE("/api/appointments")
    suspend fun deleteAppointments(): Response<Void>
}
