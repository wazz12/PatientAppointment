package com.tiimhealthcare.patientappointment.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToParent: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToParent)

fun View.visibleIfTrue(isVisible: Boolean, falseCaseValue: Int = View.GONE) {
    visibility = if (isVisible) View.VISIBLE else falseCaseValue
}