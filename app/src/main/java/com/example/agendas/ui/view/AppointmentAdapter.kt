package com.example.agendas.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agendas.data.model.Appointment
import com.example.agendas.databinding.ItemAppointmentBinding
import java.text.SimpleDateFormat

class AppointmentAdapter(
    private var appointments: MutableList<Appointment>,
    private val onDeleteClick: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    private var filteredAppoinments: MutableList<Appointment> = appointments.toMutableList()

    inner class AppointmentViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: Appointment) {
            val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy");
            binding.txtFecha.text = dateFormat.format(appointment.showDate) ?: ""
            binding.txtAsunto.text = appointment.subject ?: ""
            binding.txtActividad.text = appointment.activity ?: ""

            binding.btnDelete.setOnClickListener {
                onDeleteClick(appointment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(filteredAppoinments[position])
    }

    override fun getItemCount(): Int = filteredAppoinments.size

    fun updateList(newList: List<Appointment>) {
        appointments.clear()
        appointments.addAll(newList)
        filteredAppoinments.clear()
        filteredAppoinments.addAll(newList)
        notifyDataSetChanged()
    }


}