package me.assignment.anymind.wallet.events

import me.assignment.anymind.wallet.repositories.WalletBalanceRepository
import me.assignment.anymind.wallet.repositories.WalletTransactionRepository
import org.springframework.stereotype.Service

@Service
class WalletTransactionCreateHandler(
    private val walletBalanceRepository: WalletBalanceRepository,
    private val walletTransactionRepository: WalletTransactionRepository
) {
}