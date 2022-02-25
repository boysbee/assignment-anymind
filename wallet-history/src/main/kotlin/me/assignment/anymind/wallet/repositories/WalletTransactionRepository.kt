package me.assignment.anymind.wallet.repositories

import me.assignment.anymind.wallet.entities.WalletTransactions
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.ZonedDateTime

@Service
interface WalletTransactionRepository : CrudRepository<WalletTransactions, String> {
    @Query(
        value = """select w.id, date_trunc('hour',w.transaction_date) as  transaction_date , sum(amount) as amount 
from   wallet_transactions w 
where w.transaction_date >= to_timestamp(:startDateTime, 'YYYY-MM-DD HH24:MI:SS') 
and w.transaction_date <= to_timestamp(:endDateTime, 'YYYY-MM-DD HH24:MI:SS') 
group by 1 order by transaction_date asc""",
        nativeQuery = true
    )
    fun findTransactionByTransactionDateAndGroupByHours(@Param("startDateTime") startDateTime: Timestamp, @Param("endDateTime") endDateTime: Timestamp): List<WalletTransactions>
}