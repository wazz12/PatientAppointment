package com.tiimhealthcare.patientappointment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tiimhealthcare.patientappointment.database.PatientAppointmentDao

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val dataSource: PatientAppointmentDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientAppointmentViewModel::class.java)) {
            return PatientAppointmentViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}