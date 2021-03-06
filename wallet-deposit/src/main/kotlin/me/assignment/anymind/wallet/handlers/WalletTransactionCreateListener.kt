package me.assignment.anymind.wallet.handlers

import me.assignment.anymind.wallet.events.TransactionEvent
import me.assignment.anymind.wallet.services.WalletService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.sql.Timestamp

@Configuration
class WalletTransactionCreateListener(
    private val walletService: WalletService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Async
    @EventListener(TransactionEvent::class)
    fun saveWallet(transactionEvent: TransactionEvent) {
        this.walletService.saveTransaction(
            transactionDate = Timestamp.valueOf(transactionEvent.transactionDate.toLocalDateTime()),
            amount = transactionEvent.amount

        ).also {
            logger.info("Complete save transaction ${transactionEvent.transactionDate} ${transactionEvent.amount}")
        }

    }
}