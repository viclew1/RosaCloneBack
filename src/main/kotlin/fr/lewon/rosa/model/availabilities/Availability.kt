package fr.lewon.rosa.model.availabilities

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Availability(val startAt: LocalDateTime, val endAt: LocalDateTime) {
    val duration = ChronoUnit.MINUTES.between(startAt, endAt)
}