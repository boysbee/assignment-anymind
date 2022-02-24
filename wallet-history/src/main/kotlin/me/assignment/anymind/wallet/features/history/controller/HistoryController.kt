package me.assignment.anymind.wallet.features.history.controller

import me.assignment.anymind.wallet.features.history.models.TransactionHistory
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime

@RestController
@RequestMapping("/api/wallet/history")
class HistoryController {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun history(
        @RequestParam(required = true)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ssxxx")
        startDatetime: ZonedDateTime,
        @RequestParam(required = true)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd'T'HH:mm:ssxxx")
        endDatetime: ZonedDateTime
    ): ResponseEntity<List<TransactionHistory>> {
        logger.info("Received request to get history by startDateTime $startDatetime : endDateTime : $endDatetime")
        val response = listOf(
            TransactionHistory(
                datetime = ZonedDateTime.now(ZoneId.of("UTC")),
                amount = BigDecimal.valueOf(1000)
            ),
            TransactionHistory(
                datetime = ZonedDateTime.now(ZoneId.of("UTC")),
                amount = BigDecimal.valueOf(1001.1)
            )
        )
        return ResponseEntity.ok(response).also {
            logger.info("Response history by $response")
        }
    }
}



