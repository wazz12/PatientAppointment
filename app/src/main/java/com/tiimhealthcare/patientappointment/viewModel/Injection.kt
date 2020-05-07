package com.tiimhealthcare.patientappointment.viewModel

import android.content.Context
import com.tiimhealthcare.patientappointment.database.PatientAppointmentDao
import com.tiimhealthcare.patientappointment.database.PatientAppointmentsDatabase

/**
 * Enables injection of data sources.
 */
object Injection {

    private fun providePatientAppointmentDataSource(context: Context): PatientAppointmentDao {
        val database = PatientAppointmentsDatabase.getInstance(context)
        return database.patientAppointmentDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = providePatientAppointmentDataSource(context)
        return ViewModelFactory(dataSource)
    }
}