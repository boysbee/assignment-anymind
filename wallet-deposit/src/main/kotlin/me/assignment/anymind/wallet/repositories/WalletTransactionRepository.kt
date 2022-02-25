package me.assignment.anymind.wallet.repositories

import me.assignment.anymind.wallet.entities.WalletTransactions
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface WalletTransactionRepository : CrudRepository<WalletTransactions, String>