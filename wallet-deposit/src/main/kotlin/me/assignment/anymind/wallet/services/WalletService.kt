package me.assignment.anymind.wallet.services

import me.assignment.anymind.wallet.entities.WalletBalance
import me.assignment.anymind.wallet.entities.WalletTransactions
import me.assignment.anymind.wallet.repositories.WalletBalanceRepository
import me.assignment.anymind.wallet.repositories.WalletTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.transaction.Transactional

@Service
class WalletService(
    private val walletBalanceRepository: WalletBalanceRepository,
    private val walletTransactionRepository: WalletTransactionRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @Transactional
    fun saveTransaction(transactionDate: Timestamp, amount: BigDecimal) {
        val lastBalance = walletBalanceRepository.findTheLastBalance()
        walletTransactionRepository.save(
            WalletTransactions(
                id = UUID.randomUUID(),
                amount = amount,
                transactionDate = transactionDate
            )
        ).also {
            logger.info("End save wallet transaction $it")
        }
        walletBalanceRepository.save(
            WalletBalance(
                id = UUID.randomUUID(),
                balance = lastBalance.plus(amount),
                transactionDate = transactionDate
            )
        ).also {
            logger.info("Save save wallet balance $it")
        }
    }
}