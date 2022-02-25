package me.assignment.anymind.wallet.repositories

import me.assignment.anymind.wallet.entities.WalletBalance
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
interface WalletBalanceRepository : CrudRepository<WalletBalance, String> {
    @Query("""select w.balance from wallet_balance w order by w.transaction_date desc limit 1""", nativeQuery = true)
    fun findTheLastBalance(): BigDecimal
}