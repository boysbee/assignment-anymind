package me.assignment.anymind.wallet.features.deposit.controllers

import me.assignment.anymind.wallet.events.TransactionEvent
import me.assignment.anymind.wallet.features.deposit.models.DepositRequest
import me.assignment.anymind.wallet.features.deposit.models.DepositResponse
import me.assignment.anymind.wallet.services.SaveWalletTransactionsPublisher
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/wallet")
class DepositController(private val saveWalletTransactionsPublisher: SaveWalletTransactionsPublisher) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/deposit", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun deposit(@Valid @RequestBody request: DepositRequest): ResponseEntity<DepositResponse> {
        logger.info("Received request $request")
        this.saveWalletTransactionsPublisher.createTransaction(
            TransactionEvent(
                this,
                transactionDate = request.datetime,
                amount = request.amount
            )
        ).also {
            logger.info("End send event save transaction ..")
        }
        return ResponseEntity(DepositResponse(
            code = HttpStatus.CREATED.value(),
            message = "success"
        ), HttpStatus.CREATED).also {
            logger.info("Response $it")
        }
    }
}


