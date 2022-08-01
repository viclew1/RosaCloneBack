package fr.lewon.rosa.service

import fr.lewon.rosa.model.appointments.Appointment
import java.time.LocalDateTime

interface AppointmentService {

    fun getAppointments(from: LocalDateTime, to: LocalDateTime): List<Appointment>

}