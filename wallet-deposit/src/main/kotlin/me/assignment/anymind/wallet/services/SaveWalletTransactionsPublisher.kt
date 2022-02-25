package me.assignment.anymind.wallet.services

import me.assignment.anymind.wallet.events.TransactionEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SaveWalletTransactionsPublisher(
    private val eventPublisher: ApplicationEventPublisher
) {
    fun createTransaction(transactionEvent: TransactionEvent) {
        eventPublisher.publishEvent(transactionEvent)
    }
}