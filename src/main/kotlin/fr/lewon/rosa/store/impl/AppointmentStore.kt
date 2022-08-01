package fr.lewon.rosa.store.impl

import fr.lewon.rosa.model.appointments.Appointment
import fr.lewon.rosa.store.AbstractMockedStore
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AppointmentStore : AbstractMockedStore<Appointment>() {

    fun getAppointments(from: LocalDateTime, to: LocalDateTime): List<Appointment> {
        return items.filter { it.startAt < to && it.endAt > from }
    }

}