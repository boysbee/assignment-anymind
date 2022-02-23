package me.assignment.anymind.wallet.features.deposit.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.*

@RestController
@RequestMapping("/api/wallet")
class DepositController {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/deposit", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun deposit(@Valid @RequestBody request: DepositRequest): ResponseEntity<DepositResponse> {
        logger.info("Received request $request")
        val response = DepositResponse(
            code = 0,
            message = "success"
        )
        return ResponseEntity.ok(response).also {
            logger.info("Response $response")
        }
    }
}

data class DepositRequest(
    @field:NotNull
    val datetime: ZonedDateTime,
    @field:NotNull
    @field:DecimalMin(value = "0.0", inclusive = false, message = "The decimal value can not be less than 0.0")
    @field:DecimalMax(value = "9999999999.99999", inclusive = false, message = "The decimal value can not be more than 9999999999.99999")
    val amount: BigDecimal
)

data class DepositResponse(
    val code: Int,
    val message: String
)