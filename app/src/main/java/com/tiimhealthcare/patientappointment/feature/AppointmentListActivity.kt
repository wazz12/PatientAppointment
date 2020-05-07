package com.tiimhealthcare.patientappointment.feature

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiimhealthcare.patientappointment.R
import com.tiimhealthcare.patientappointment.database.PatientAppointment
import com.tiimhealthcare.patientappointment.model.Gender
import com.tiimhealthcare.patientappointment.util.showAlertDialog
import com.tiimhealthcare.patientappointment.util.showDatePicker
import com.tiimhealthcare.patientappointment.util.visibleIfTrue
import com.tiimhealthcare.patientappointment.viewModel.Injection
import com.tiimhealthcare.patientappointment.viewModel.PatientAppointmentViewModel
import com.tiimhealthcare.patientappointment.viewModel.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_appointment_list.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.dialog_add_appointment.view.*
import java.util.*

class AppointmentListActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: PatientAppointmentViewModel by viewModels { viewModelFactory }
    private val disposable = CompositeDisposable()

    private var appointmentListAdapter = AppointmentListAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_list)

        setToolBar(getString(R.string.appointment_list))
        viewModelFactory = Injection.provideViewModelFactory(this)
        getAppointmentList()
    }

    private fun setToolBar(title: String) {
        toolbar.appToolbarTitle(title)
        btnToolbarAction.visibleIfTrue(true)
        btnToolbarAction.text = getString(R.string.add)
        btnToolbarAction.setOnClickListener {
            showAddAppointmentDialog()
        }
    }

    private fun getAppointmentList() {
        // Subscribe to the emissions of all the patientAppointments from the view model.
        disposable.add(
            viewModel.getAllAppointments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.setAppointmentList(it) },
                    { error ->
                        showAlertDialog(
                            this,
                            getString(R.string.alert),
                            error.toString()
                        )
                    })
        )
    }

    private fun setAppointmentList(patientAppointmentList: List<PatientAppointment>) {
        empty_text_view.visibleIfTrue(patientAppointmentList.isEmpty())
        recycler_view.visibleIfTrue(patientAppointmentList.isNotEmpty())
        appointmentListAdapter =
            AppointmentListAdapter(
                patientAppointmentList
            )
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = appointmentListAdapter
    }

    private fun showAddAppointmentDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_add_appointment, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(true)
        val dialog = alertDialogBuilder.create()

        val birthDateCalendar = Calendar.getInstance()
        view.patient_dob_edit_text.setOnClickListener {
            showDatePicker(
                this,
                birthDateCalendar,
                -1,
                Date().time,
                view.patient_dob_edit_text
            )
        }

        // Bind Spinner to Gender enum
        var gender = Gender.MALE
        view.patient_gender_spinner.adapter =
            ArrayAdapter<Gender>(
                this,
                android.R.layout.simple_selectable_list_item,
                Gender.values()
            )
        view.patient_gender_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    gender = Gender.values()[position]
                    Log.e("gender", gender.name)
                }
            }

        val appointmentDateCalendar = Calendar.getInstance()
        view.patient_appointment_date_edit_text.setOnClickListener {
            showDatePicker(
                this,
                appointmentDateCalendar,
                Date().time,
                -1,
                view.patient_appointment_date_edit_text
            )
        }

        view.add_button.setOnClickListener {
            var isValid = true
            val name = view.patient_name_edit_text.text.toString()
            if (name.isEmpty()) {
                view.patient_name_edit_text.error = getString(R.string.enter_name)
                isValid = false
            }
            val birthDate = birthDateCalendar.time
            if (view.patient_dob_edit_text.text.isEmpty()) {
                view.patient_dob_edit_text.error = getString(R.string.select_date)
                isValid = false
            }
            val appointmentDate = appointmentDateCalendar.time
            if (view.patient_appointment_date_edit_text.text.isEmpty()) {
                view.patient_appointment_date_edit_text.error = getString(R.string.select_date)
                isValid = false
            }

            if (isValid) {
                val patientAppointment = PatientAppointment(
                    UUID.randomUUID().toString(),
                    name,
                    birthDate,
                    gender,
                    appointmentDate
                )
                // Subscribe to adding the patientAppointment.
                disposable.add(
                    viewModel.addPatientAppointment(patientAppointment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            showAlertDialog(
                                this,
                                getString(R.string.success),
                                getString(R.string.appointment_add_success)
                            )
                            dialog.dismiss()
                            getAppointmentList()
                        },
                            { error ->
                                showAlertDialog(
                                    this,
                                    getString(R.string.alert),
                                    error.toString()
                                )
                            })
                )
            }
        }
        dialog.show()
    }
}