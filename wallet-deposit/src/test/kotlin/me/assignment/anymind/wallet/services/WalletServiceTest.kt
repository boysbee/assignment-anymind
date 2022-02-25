package me.assignment.anymind.wallet.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import me.assignment.anymind.wallet.repositories.WalletBalanceRepository
import me.assignment.anymind.wallet.repositories.WalletTransactionRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import java.sql.SQLIntegrityConstraintViolationException
import java.sql.Timestamp
import java.time.LocalDateTime

internal class WalletServiceTest {
    private lateinit var walletService: WalletService
    private val walletTransactionRepository: WalletTransactionRepository = mockk()
    private val walletBalanceRepository: WalletBalanceRepository = mockk()


    @BeforeEach
    fun setUp() {
        walletService = WalletService(
            walletBalanceRepository,
            walletTransactionRepository
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `it should call save wallet balance and wallet transaction successfully`() {
        val transactionDate = Timestamp.valueOf(LocalDateTime.now())
        val amount = 1.toBigDecimal()
        every { walletBalanceRepository.findTheLastBalance() } returns 100.toBigDecimal()
        every { walletBalanceRepository.save(any()) } returns mockk()
        every { walletTransactionRepository.save(any()) } returns mockk()
        walletService.saveTransaction(transactionDate, amount)
        verify(exactly = 1) { walletBalanceRepository.findTheLastBalance() }
        verify(exactly = 1) { walletBalanceRepository.save(any()) }
        verify(exactly = 1) { walletTransactionRepository.save(any()) }
    }

    @Test
    fun `it should not call save balance and transaction when find balance throw exception`() {
        val transactionDate = Timestamp.valueOf(LocalDateTime.now())
        val amount = 1.toBigDecimal()
        every { walletBalanceRepository.findTheLastBalance() } throws SQLIntegrityConstraintViolationException()
        assertThrows<SQLIntegrityConstraintViolationException> {
            walletService.saveTransaction(
                transactionDate,
                amount
            )
        }
        verify(exactly = 1) { walletBalanceRepository.findTheLastBalance() }
        verify(exactly = 0) { walletBalanceRepository.save(any()) }
        verify(exactly = 0) { walletTransactionRepository.save(any()) }
    }

    @Test
    fun `it should called all services when save wallet balance save throw exception`() {
        val transactionDate = Timestamp.valueOf(LocalDateTime.now())
        val amount = 1.toBigDecimal()
        every { walletBalanceRepository.findTheLastBalance() } returns 100.toBigDecimal()
        every { walletTransactionRepository.save(any()) } returns mockk()
        every { walletBalanceRepository.save(any()) } throws SQLIntegrityConstraintViolationException()
        assertThrows<SQLIntegrityConstraintViolationException> {
            walletService.saveTransaction(
                transactionDate,
                amount
            )
        }
        verify(exactly = 1) { walletBalanceRepository.findTheLastBalance() }
        verify(exactly = 1) { walletBalanceRepository.save(any()) }
        verify(exactly = 1) { walletTransactionRepository.save(any()) }
    }

    @Test
    fun `it should call services to save wallet balance when save wallet transaction throw exception`() {
        val transactionDate = Timestamp.valueOf(LocalDateTime.now())
        val amount = 1.toBigDecimal()
        every { walletBalanceRepository.findTheLastBalance() } returns 100.toBigDecimal()
        every { walletTransactionRepository.save(any()) } throws SQLIntegrityConstraintViolationException()
        assertThrows<SQLIntegrityConstraintViolationException> {
            walletService.saveTransaction(
                transactionDate,
                amount
            )
        }
        verify(exactly = 1) { walletBalanceRepository.findTheLastBalance() }
        verify(exactly = 1) { walletTransactionRepository.save(any()) }
        verify(exactly = 0) { walletBalanceRepository.save(any()) }
    }


}
