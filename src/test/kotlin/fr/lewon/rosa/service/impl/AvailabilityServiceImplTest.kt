package fr.lewon.rosa.service.impl

import com.ninjasquad.springmockk.MockkBean
import fr.lewon.rosa.model.appointments.Appointment
import fr.lewon.rosa.service.AvailabilityService
import fr.lewon.rosa.store.impl.AppointmentStore
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@WebMvcTest
@Import(AvailabilityServiceImpl::class, AppointmentServiceImpl::class)
class AvailabilityServiceImplTest {

    @Autowired
    private lateinit var availabilityService: AvailabilityService

    @MockkBean
    private lateinit var appointmentStore: AppointmentStore

    @Test
    fun getAvailabilitiesTest() {
        // Mocked appointmentStore to return 3 appointments :
        // 25/05/2022 : 12:00 -> 16:00
        // 26/05/2022 : 9:30 -> 11:00 + 18:00 -> 20:00
        val appointments = listOf(
            Appointment(LocalDateTime.of(2022, 5, 25, 12, 0), LocalDateTime.of(2022, 5, 25, 16, 0)),
            Appointment(LocalDateTime.of(2022, 5, 26, 9, 30), LocalDateTime.of(2022, 5, 26, 11, 0)),
            Appointment(LocalDateTime.of(2022, 5, 26, 18, 0), LocalDateTime.of(2022, 5, 26, 20, 0))
        )
        every { appointmentStore.getAppointments(any(), any()) } returns appointments

        // Getting availabilities between 24/05 - 22:00 and 26/05 - 22:00
        val availabilities = availabilityService.getAvailabilities(
            LocalDateTime.of(2022, 5, 24, 22, 0),
            LocalDateTime.of(2022, 5, 26, 22, 0)
        )

        // There should be 3 availabilities
        Assertions.assertEquals(3, availabilities.size)
        // First one on 25/05 between 09:30 and 12:00
        val availability1 = availabilities[0]
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 25, 9, 30), availability1.startAt)
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 25, 12, 0), availability1.endAt)
        // Second one on 25/05 between 16:00 and 20:00
        val availability2 = availabilities[1]
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 25, 16, 0), availability2.startAt)
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 25, 20, 0), availability2.endAt)
        // Third one on 26/05 between 11:00 and 18:00
        val availability3 = availabilities[2]
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 26, 11, 0), availability3.startAt)
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 26, 18, 0), availability3.endAt)
    }

}