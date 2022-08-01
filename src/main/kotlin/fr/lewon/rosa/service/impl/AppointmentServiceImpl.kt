package fr.lewon.rosa.service.impl

import fr.lewon.rosa.model.appointments.Appointment
import fr.lewon.rosa.service.AppointmentService
import fr.lewon.rosa.store.impl.AppointmentStore
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AppointmentServiceImpl(private val appointmentStore: AppointmentStore) : AppointmentService {

    override fun getAppointments(from: LocalDateTime, to: LocalDateTime): List<Appointment> {
        return appointmentStore.getAppointments(from, to).sortedBy { it.startAt }
    }

}