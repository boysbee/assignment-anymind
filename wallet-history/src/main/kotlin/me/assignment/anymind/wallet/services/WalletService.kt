package me.assignment.anymind.wallet.services

import me.assignment.anymind.wallet.features.history.models.TransactionHistory
import me.assignment.anymind.wallet.repositories.WalletTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class WalletService(val walletRepository: WalletTransactionRepository) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun findHistory(startDate: LocalDateTime, endDateTime: LocalDateTime): List<TransactionHistory> {
        val list = walletRepository.findTransactionByTransactionDateAndGroupByHours(
            Timestamp.valueOf(startDate), Timestamp.valueOf(endDateTime)
        )

         return list.groupingBy { it.transactionDate }.eachSumBy { it.amount }
            .map {
                TransactionHistory(
                    datetime = ZonedDateTime.of(it.key.toLocalDateTime(), ZoneId.of("UTC")),
                    amount = it.value
                )
            }.toList()
    }
}

fun <T, K> Grouping<T, K>.eachSumBy(
    selector: (T) -> BigDecimal
): Map<K, BigDecimal> =
    fold(BigDecimal.ZERO) { acc, elem -> acc.plus(selector(elem)) }