package com.example.agendas.data.repository

import android.util.Log
import com.example.agendas.data.model.Appointment
import com.example.agendas.data.remote.AppointmentApiService

class AppointmentRepository(private val apiService: AppointmentApiService) {

    suspend fun getAppoinments(): List<Appointment>? {
        return try {
            val response = apiService.getAppointments()
            if (response.isSuccessful) {
                Log.d("AgendaRepository", "get appointments successfully.")
                response.body()
            } else {
                Log.e(
                    "getAppointments",
                    "Error while get appointments: ${response.code()} - ${response.message()}"
                )
                null
            }
        } catch (e: Exception) {
            Log.e("getAppointments", "Error: ${e.localizedMessage}")
            null
        }
    }


    suspend fun addAppointment(appointment: Appointment): Boolean {
        return try {
            val response = apiService.addAppointment(appointment)
            if (response.isSuccessful) {
                Log.d("addAppointment", "appointment created successfully.")
                true
            } else {
                Log.e(
                    "addAppointment",
                    "Error while add appointment: ${response.code()} - ${response.message()}"
                )
                false
            }
        } catch (e: Exception) {
            Log.e("addAppointment", "Error: ${e.localizedMessage}")
            false
        }
    }

    suspend fun deleteAppointment(id: Long): Boolean {
        return try {
            val response = apiService.deleteAppointment(id)
            if (response.isSuccessful) {
                Log.d("deleteAppointment", "appointment with ID: $id deleted.")
                true
            } else {
                Log.e(
                    "deleteAppointment",
                    "Error while delete appointment: ${response.code()} - ${response.message()}"
                )
                false
            }
        } catch (e: Exception) {
            Log.e("deleteAppointment", "Error: ${e.localizedMessage}")
            false
        }
    }

    suspend fun deleteAppointments(): Boolean {
        return try {
            val response = apiService.deleteAppointments()
            if (response.isSuccessful) {
                Log.d("deleteAppointments", "appointments deleted.")
                true
            } else {
                Log.e(
                    "deleteAppointments",
                    "Error while delete appointments: ${response.code()} - ${response.message()}"
                )
                false
            }
        } catch (e: Exception) {
            Log.e("deleteAppointments", "Error: ${e.localizedMessage}")
            false
        }
    }
}