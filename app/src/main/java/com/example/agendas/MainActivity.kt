package com.example.agendas

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agendas.databinding.ActivityMainBinding
import com.example.agendas.ui.service.ServiceAppointmentHandler
import com.example.agendas.ui.view.AppointmentAdapter
import com.example.agendas.ui.viewmodel.AppointmentViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val appointmentViewModel: AppointmentViewModel by viewModels()
    private lateinit var adapter: AppointmentAdapter
    private lateinit var appointmentHandler: ServiceAppointmentHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        adapter = AppointmentAdapter(mutableListOf(),
            onDeleteClick = { agenda ->
                agenda.appointmentID?.let { appointmentHandler.deleteAppointment(it) }
            }
        )
        binding.recyclerView.adapter = adapter

        appointmentHandler = ServiceAppointmentHandler(this, appointmentViewModel, lifecycleScope)

        // watch the changes in appointments list and update UI
        lifecycleScope.launch {
            appointmentViewModel.appointments.collectLatest { appointments ->
                adapter.updateList(appointments)
            }
        }

        appointmentViewModel.fetchAppointments()

        binding.btnAddAppointment.setOnClickListener {
            appointmentHandler.addAppointment()
        }

        binding.btnRemoveAppointments.setOnClickListener {
            appointmentHandler.removeAppointments()
        }
    }
}