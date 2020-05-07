package com.tiimhealthcare.patientappointment.model

enum class Gender(private val stringGender: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    UNKNOWN("Unknown");

    override fun toString(): String {
        return stringGender
    }
}