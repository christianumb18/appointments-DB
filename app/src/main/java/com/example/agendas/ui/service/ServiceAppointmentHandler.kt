package com.example.agendas.ui.service

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.agendas.R
import com.example.agendas.data.model.Appointment
import com.example.agendas.ui.viewmodel.AppointmentViewModel
import kotlinx.coroutines.launch

class ServiceAppointmentHandler(
    private val context: Context,
   private val agendaViewModel: AppointmentViewModel,
   private val lifecycleScope: LifecycleCoroutineScope,
) {
    fun deleteAppointment(id: Long) {
        lifecycleScope.launch {
            val success = agendaViewModel.deleteAppointment(id)
            if (success) {
                Toast.makeText(context, "appointment deleted", Toast.LENGTH_SHORT).show()
                agendaViewModel.fetchAppointments() // reload list after of appointment added
            } else {
                Toast.makeText(context, "Ocurrio un error al eliminar la agenda", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addAppointment() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.modal_add_apointment, null)
        val txtEditDate = dialogView.findViewById<EditText>(R.id.txtEditDate)
        val txtEditSubject = dialogView.findViewById<EditText>(R.id.txtEditSubject)
        val txtEditActivity = dialogView.findViewById<EditText>(R.id.txtEditActivity)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Agregar Nueva Agenda")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val date = txtEditDate.text.toString()
                val subject = txtEditSubject.text.toString()
                val activity = txtEditActivity.text.toString()

                if (date.isNotEmpty() && subject.isNotEmpty() && activity.isNotEmpty()) {
                    val newAppointment = Appointment(date = date, subject = subject, activity = activity)
                    lifecycleScope.launch {
                        val success = agendaViewModel.addAppointment(newAppointment)
                        if (success) {
                            Toast.makeText(context, "Agenda creada", Toast.LENGTH_SHORT).show()
                            agendaViewModel.fetchAppointments()
                        } else {
                            Toast.makeText(context, "Ocurrio un error al agregar la agenda", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}