package fr.lewon.rosa.store.impl

import fr.lewon.rosa.model.appointments.Appointment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class AppointmentStoreTest {

    @Autowired
    private lateinit var appointmentStore: AppointmentStore

    @Test
    fun getAppointmentsTest() {
        // 3 appointments added :
        // 25/05/2022 : 12:00 -> 16:00
        // 26/05/2022 : 9:30 -> 11:00 + 18:00 -> 20:00
        val appointment1 = Appointment(LocalDateTime.of(2022, 5, 25, 12, 0), LocalDateTime.of(2022, 5, 25, 16, 0))
        val appointment2 = Appointment(LocalDateTime.of(2022, 5, 26, 9, 30), LocalDateTime.of(2022, 5, 26, 11, 0))
        val appointment3 = Appointment(LocalDateTime.of(2022, 5, 26, 18, 0), LocalDateTime.of(2022, 5, 26, 20, 0))
        appointmentStore.add(appointment1)
        appointmentStore.add(appointment2)
        appointmentStore.add(appointment3)

        // Getting availabilities between 24/05 - 22:00 and 26/05 - 10:00
        val appointments = appointmentStore.getAppointments(
            LocalDateTime.of(2022, 5, 24, 22, 0),
            LocalDateTime.of(2022, 5, 26, 10, 0)
        )

        //There should be 2 appointments found
        Assertions.assertEquals(2, appointments.size)
        Assertions.assertTrue(appointments.contains(appointment1))
        Assertions.assertTrue(appointments.contains(appointment2))
    }

}