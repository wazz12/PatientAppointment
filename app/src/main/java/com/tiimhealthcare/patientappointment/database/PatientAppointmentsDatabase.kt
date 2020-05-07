package com.tiimhealthcare.patientappointment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room database that contains the PatientAppointments table
 */
@Database(entities = [PatientAppointment::class], version = 1)
@TypeConverters(Converters::class)
abstract class PatientAppointmentsDatabase : RoomDatabase() {

    abstract fun patientAppointmentDao(): PatientAppointmentDao

    companion object {

        @Volatile
        private var INSTANCE: PatientAppointmentsDatabase? = null

        fun getInstance(context: Context): PatientAppointmentsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PatientAppointmentsDatabase::class.java, "PatientAppointment.db"
            )
                .build()
    }
}