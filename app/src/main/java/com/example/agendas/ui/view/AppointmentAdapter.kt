package com.example.agendas.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agendas.data.model.Appointment
import com.example.agendas.databinding.ItemAppointmentBinding

class AppointmentAdapter(
    private var appointments: MutableList<Appointment>,
    private val onDeleteClick: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    private var filteredAppoinments: MutableList<Appointment> = appointments.toMutableList()

    inner class AppointmentViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(agenda: Appointment) {
            binding.txtFecha.text = agenda.date ?: ""
            binding.txtAsunto.text = agenda.subject ?: ""
            binding.txtActividad.text = agenda.activity ?: ""

            binding.btnDelete.setOnClickListener {
                onDeleteClick(agenda)
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