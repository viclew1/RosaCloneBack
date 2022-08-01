package fr.lewon.rosa

import fr.lewon.rosa.model.appointments.Appointment
import fr.lewon.rosa.store.impl.AppointmentStore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDateTime

@SpringBootApplication
class RosaCloneApplication

fun main(args: Array<String>) {
    val app = runApplication<RosaCloneApplication>(*args)

    // Initial data added for testing
    val appointmentStore = app.getBean("appointmentStore") as AppointmentStore
    appointmentStore.add(Appointment(LocalDateTime.of(2022, 5, 25, 12, 0), LocalDateTime.of(2022, 5, 25, 16, 0)))
    appointmentStore.add(Appointment(LocalDateTime.of(2022, 5, 26, 9, 30), LocalDateTime.of(2022, 5, 26, 11, 0)))
    appointmentStore.add(Appointment(LocalDateTime.of(2022, 5, 26, 18, 0), LocalDateTime.of(2022, 5, 26, 20, 0)))
}
