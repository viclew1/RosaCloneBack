package fr.lewon.rosa.web.controllers

import fr.lewon.rosa.model.availabilities.Availability
import fr.lewon.rosa.service.AvailabilityService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/availabilities")
class AvailabilityController(private val availabilityService: AvailabilityService) {

    @GetMapping
    fun getAvailabilities(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) to: LocalDateTime
    ): ResponseEntity<List<Availability>> {
        return ResponseEntity.ok(availabilityService.getAvailabilities(from, to))
    }

    @GetMapping("/next")
    fun getNextAvailability(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) from: LocalDateTime
    ): ResponseEntity<Availability> {
        return ResponseEntity.ok(availabilityService.getNextAvailability(from))
    }

}