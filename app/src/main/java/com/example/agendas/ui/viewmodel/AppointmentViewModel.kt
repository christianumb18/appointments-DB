package com.example.agendas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agendas.data.model.Appointment
import com.example.agendas.data.remote.RetrofitClient
import com.example.agendas.data.repository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {
    private val repository = AppointmentRepository(RetrofitClient.apiService)

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    fun fetchAppointments() {
        viewModelScope.launch {
            val completeList = repository.getAppoinments()
            _appointments.value = completeList ?: emptyList()
        }
    }


    suspend fun deleteAppointment(id: Long): Boolean {
        val result = repository.deleteAppointment(id)
        if (result) {
            fetchAppointments()
        }
        return result
    }

    suspend fun addAppointment(appointment: Appointment): Boolean {
        val result = repository.addAppointment(appointment)
        if (result) {
            fetchAppointments()
        }
        return result
    }

    suspend fun deleteAppointments(): Boolean {
        val result = repository.deleteAppointments()
        if (result) {
            fetchAppointments()
        }
        return result
    }
}