package fr.lewon.rosa.service

import fr.lewon.rosa.model.availabilities.Availability
import java.time.LocalDateTime

interface AvailabilityService {

    fun getAvailabilities(from: LocalDateTime, to: LocalDateTime): List<Availability>

    fun getNextAvailability(from: LocalDateTime): Availability?

}