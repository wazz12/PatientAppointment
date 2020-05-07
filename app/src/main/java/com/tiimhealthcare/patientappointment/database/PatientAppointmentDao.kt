package com.tiimhealthcare.patientappointment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface PatientAppointmentDao {

    /**
     * Get all patientAppointments.
     * @return all the patientAppointments from the table.
     */
    @Query("SELECT * FROM patient_appointments ORDER BY appointmentDate ASC")
    fun getAllAppointments(): Flowable<List<PatientAppointment>>

    /**
     * Insert a patientAppointment in the database. If the patientAppointment already exists, replace it.
     * @param patientAppointment the patientAppointment to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatientAppointment(patientAppointment: PatientAppointment): Completable
}