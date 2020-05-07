package com.tiimhealthcare.patientappointment.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tiimhealthcare.patientappointment.database.PatientAppointment
import com.tiimhealthcare.patientappointment.database.PatientAppointmentDao
import io.reactivex.Completable
import io.reactivex.Flowable

class PatientAppointmentViewModel(private val dataSource: PatientAppointmentDao) : ViewModel() {

    /**
     * Get all the appointments.
     * @returns a List of patientAppointments.
     */
    fun getAllAppointments(): Flowable<List<PatientAppointment>> {
        return dataSource.getAllAppointments()
    }

    /**
     * Add the patientAppointment.
     * @param patientAppointment the new patientAppointment
     * *
     * @returns a [Completable] that completes when the patientAppointment is added
     */
    fun addPatientAppointment(patientAppointment: PatientAppointment): Completable {
        Log.e("patientAppointment", patientAppointment.toString())
        return dataSource.insertPatientAppointment(patientAppointment)
    }
}