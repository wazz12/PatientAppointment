package com.tiimhealthcare.patientappointment.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.tiimhealthcare.patientappointment.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Convert date to default format pattern
 * @param date
 * @return string formatted date return
 */
fun convertDateToDefaultFormat(context: Context, date: Date): String? {
    val format = SimpleDateFormat(context.getString(R.string.default_date_format), Locale.US)
    return format.format(date)
}

fun showDatePickerDialog(
    context: Context,
    calendar: Calendar,
    minDate: Long,
    maxDate: Long,
    onDateSetListener: DatePickerDialog.OnDateSetListener
) {
    val datePickerDialog = DatePickerDialog(
        context, onDateSetListener,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val datePicker = datePickerDialog.datePicker
    datePicker.minDate = minDate
    if (maxDate >= 0)
        datePicker.maxDate = maxDate
    datePicker.tag = calendar
    datePickerDialog.show()
}

fun showDatePicker(
    context: Context,
    calendar: Calendar,
    minDate: Long,
    maxDate: Long,
    editText: EditText
) {
    showDatePickerDialog(
        context,
        calendar,
        minDate,
        maxDate,
        DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            if (view.isShown) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val selectedDate = convertDateToDefaultFormat(context, calendar.time)
                editText.tag = calendar
                editText.error = null
                editText.setText(selectedDate)
            }
        })
}

fun showAlertDialog(context: Context, title: String, message: String) {
    val dialog = AlertDialog.Builder(context)
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.setPositiveButton(android.R.string.ok, null)
    dialog.show()
}