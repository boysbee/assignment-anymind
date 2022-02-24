package me.assignment.anymind.wallet.features.history.controller

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.validation.Valid
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

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

data class HistoriesCriteria(
    @field:NotNull
    val startDatetime: ZonedDateTime,
    @field:NotNull
    val endDatetime: ZonedDateTime
)

@JsonIgnoreProperties
data class TransactionHistory(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssxxx", timezone = "UTC")
    val datetime: ZonedDateTime,
    val amount: BigDecimal
)

data class HistoryResponse(
    val transactionHistories: List<TransactionHistory>
)

data class CommonResponse(val code: Int, val message: String)