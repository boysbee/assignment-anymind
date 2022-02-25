package me.assignment.anymind.wallet.services

import io.mockk.*
import me.assignment.anymind.wallet.events.TransactionEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import java.time.ZonedDateTime

internal class SaveWalletTransactionsPublisherTest {

    private lateinit var saveWalletTransactionsPublisher: SaveWalletTransactionsPublisher

    private val eventPublisher: ApplicationEventPublisher = mockk()

    @BeforeEach
    fun setUp() {
        saveWalletTransactionsPublisher = SaveWalletTransactionsPublisher(eventPublisher)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should call event publisher once time`() {
        val fakeTransactionEvent = TransactionEvent(
            this,
            transactionDate = ZonedDateTime.now(),
            amount = 10.toBigDecimal()
        )
        every { eventPublisher.publishEvent(fakeTransactionEvent) } just Runs
        saveWalletTransactionsPublisher.createTransaction(fakeTransactionEvent)
        verify(exactly = 1) { eventPublisher.publishEvent(fakeTransactionEvent) }
    }
}