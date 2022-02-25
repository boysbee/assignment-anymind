package me.assignment.anymind.wallet.events

import org.springframework.context.ApplicationEvent
import java.math.BigDecimal
import java.time.ZonedDateTime

class TransactionEvent(
    source: Any,
    val transactionDate: ZonedDateTime,
    val amount: BigDecimal,
) : ApplicationEvent(source), java.io.Serializable