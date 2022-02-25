package me.assignment.anymind.wallet

import me.assignment.anymind.wallet.entities.WalletTransactions
import me.assignment.anymind.wallet.repositories.WalletTransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


@SpringBootApplication
class Application {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    lateinit var walletTransactionRepository: WalletTransactionRepository

    @EventListener(ApplicationReadyEvent::class)
    fun runAfterStartup() {


        val list = mutableListOf<WalletTransactions>(
            WalletTransactions(
                id = UUID.randomUUID(), 101.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(-2))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 20.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(-20))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 1000.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(-1))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 999.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 100.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(1))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 123.1.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusHours(2))
            ),
            WalletTransactions(
                id = UUID.randomUUID(), 9.10.toBigDecimal(),
                Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(20))
            )
        )
        logger.info("Saving new transactions...")
        this.walletTransactionRepository.saveAll(list)

    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)


}