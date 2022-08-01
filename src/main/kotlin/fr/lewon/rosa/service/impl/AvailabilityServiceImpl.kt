package fr.lewon.rosa.service.impl

import fr.lewon.rosa.model.appointments.Appointment
import fr.lewon.rosa.model.availabilities.Availability
import fr.lewon.rosa.service.AppointmentService
import fr.lewon.rosa.service.AvailabilityService
import org.springframework.stereotype.Service
import java.time.DayOfWeek.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.streams.toList

@Service
class AvailabilityServiceImpl(private val appointmentService: AppointmentService) : AvailabilityService {

    // Constants that should be parameterized by health professional
    companion object {
        private val DAY_START = LocalTime.of(9, 30)
        private val DAY_END = LocalTime.of(20, 0)
        private val VALID_DAYS = listOf(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
        private const val MAX_DELTA_DAYS = 30
    }

    override fun getAvailabilities(from: LocalDateTime, to: LocalDateTime): List<Availability> {
        val appointments = appointmentService.getAppointments(from, to)
        return getValidDatesBetween(from.toLocalDate(), to.toLocalDate())
            .flatMap { getDateAvailabilities(it, from, to, appointments) }
    }

    override fun getNextAvailability(from: LocalDateTime): Availability? {
        val to = LocalDateTime.now().plusDays(30)
        val appointments = appointmentService.getAppointments(from, to)
        for (date in getValidDatesBetween(from.toLocalDate(), to.toLocalDate())) {
            getDateAvailabilities(date, from, to, appointments).firstOrNull()?.let { return it }
        }
        return null
    }

    private fun getValidDatesBetween(from: LocalDate, to: LocalDate): List<LocalDate> {
        return from.datesUntil(to.plusDays(1)).filter(this::isDateValid).toList()
    }

    private fun isDateValid(date: LocalDate): Boolean {
        return date.dayOfWeek in VALID_DAYS && ChronoUnit.DAYS.between(LocalDate.now(), date) < MAX_DELTA_DAYS
    }

    private fun getDateAvailabilities(
        date: LocalDate,
        minDateTime: LocalDateTime,
        maxDateTime: LocalDateTime,
        appointments: List<Appointment>
    ): List<Availability> {
        val start = if (minDateTime.toLocalDate() == date && minDateTime.toLocalTime().isAfter(DAY_START)) {
            minDateTime
        } else date.atTime(DAY_START)
        val end = if (maxDateTime.toLocalDate() == date && maxDateTime.toLocalTime().isBefore(DAY_END)) {
            maxDateTime
        } else date.atTime(DAY_END)
        val appointmentsThisDate = appointments.filter {
            it.startAt.toLocalDate() == date || it.endAt.toLocalDate() == date
        }
        return getAvailabilities(start, end, appointmentsThisDate)
    }

    private fun getAvailabilities(
        from: LocalDateTime,
        to: LocalDateTime,
        appointments: List<Appointment>
    ): List<Availability> {
        val availabilities = ArrayList<Availability>()
        var availabilityStart = from
        for (appointment in appointments) {
            availabilities.add(Availability(availabilityStart, appointment.startAt))
            availabilityStart = appointment.endAt
        }
        availabilities.add(Availability(availabilityStart, to))
        return availabilities.filter { it.duration > 0 }
    }

}