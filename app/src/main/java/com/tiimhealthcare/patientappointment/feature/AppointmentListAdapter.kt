package com.tiimhealthcare.patientappointment.feature

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiimhealthcare.patientappointment.R
import com.tiimhealthcare.patientappointment.database.PatientAppointment
import com.tiimhealthcare.patientappointment.util.convertDateToDefaultFormat
import com.tiimhealthcare.patientappointment.util.inflate
import kotlinx.android.synthetic.main.appointment_list_item.view.*

class AppointmentListAdapter(private val patientAppointmentList: List<PatientAppointment>) :
    RecyclerView.Adapter<AppointmentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.appointment_list_item))

    override fun getItemCount() = patientAppointmentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(patientAppointmentList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(appointment: PatientAppointment) {
            with(itemView) {
                patient_name_text_view.text = appointment.name
                patient_dob_text_view.text =
                    convertDateToDefaultFormat(context, appointment.birthDate)
                patient_gender_text_view.text = appointment.gender.name
                appointment_date_text_view.text =
                    convertDateToDefaultFormat(context, appointment.appointmentDate)
            }
        }
    }
}