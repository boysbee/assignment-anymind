package me.assignment.anymind.wallet.features.deposit.controllers

import me.assignment.anymind.wallet.features.deposit.models.DepositRequest
import me.assignment.anymind.wallet.features.deposit.models.DepositResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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


