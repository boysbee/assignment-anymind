package me.assignment.anymind.wallet.services

import io.mockk.MockK
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import me.assignment.anymind.wallet.entities.WalletBalance
import me.assignment.anymind.wallet.features.history.models.TransactionHistory
import me.assignment.anymind.wallet.repositories.WalletBalanceRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

internal class WalletServiceTest {
    private lateinit var walletService: WalletService
    private val walletBalanceRepository: WalletBalanceRepository = mockk()

    @BeforeEach
    fun setUp() {
        walletService = WalletService(walletBalanceRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `find balance history successfully`() {

        val startDatetime = LocalDateTime.now().plusDays(-1)
        val endDateTime = LocalDateTime.now().plusDays(1)
        val firstRecordTime = Timestamp.valueOf(LocalDateTime.now().plusDays(-1))

        every { walletBalanceRepository.findBalanceByTransactionDateAndGroupByHours(any(), any()) } returns listOf(
            WalletBalance(
                id = UUID.randomUUID(),
                balance = 1000.toBigDecimal(),
                transactionDate = firstRecordTime
            ),
            WalletBalance(
                id = UUID.randomUUID(),
                balance = 1020.1.toBigDecimal(),
                transactionDate = Timestamp.valueOf(LocalDateTime.now().plusDays(1))
            )
        )
        val list = walletService.findBalanceHistory(
            startDatetime, endDateTime
        )
        assertEquals(list[0].amount, 1000.toBigDecimal())
        assertEquals(list[1].amount, 1020.1.toBigDecimal())
    }


    @Test
    fun `find balance history not found should be empty list`() {

        val startDatetime = LocalDateTime.now().plusDays(-1)
        val endDateTime = LocalDateTime.now().plusDays(1)


        every { walletBalanceRepository.findBalanceByTransactionDateAndGroupByHours(any(), any()) } returns listOf()
        val list = walletService.findBalanceHistory(
            startDatetime, endDateTime
        )
        assertTrue(list.isEmpty())
    }

}