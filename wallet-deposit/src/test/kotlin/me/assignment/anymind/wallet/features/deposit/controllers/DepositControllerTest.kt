package me.assignment.anymind.wallet.features.deposit.controllers

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import me.assignment.anymind.wallet.handlers.GlobalExceptionHandlers
import me.assignment.anymind.wallet.services.SaveWalletTransactionsPublisher
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

internal class DepositControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var depositController: DepositController
    private val saveWalletTransactionsPublisher: SaveWalletTransactionsPublisher = mockk()

    @BeforeEach
    fun setUp() {
        depositController = DepositController(saveWalletTransactionsPublisher)
        mockMvc = standaloneSetup(depositController)
            .setControllerAdvice(GlobalExceptionHandlers())
            .build()

        every { saveWalletTransactionsPublisher.createTransaction(any()) } just Runs
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `deposit with valid request should be successfully`() {
        mockMvc.perform(
            post("/api/wallet/deposit")
                .content(
                    """{
                    "datetime": "2019-10-05T14:48:01+01:00",
                    "amount": 1.1
               }"""
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().json("""{ "code" : ${HttpStatus.CREATED.value()}, "message" : "success"}"""))
    }

    @Test
    fun `should be found bad request when datetime is empty`() {
        mockMvc.perform(
            post("/api/wallet/deposit").content(
                """{
                    "datetime": "",
                    "amount": 1.1
               }"""
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{ "code" : 400, "message" : "datetime in request is required or not valid format."}"""))
    }

    @Test
    fun `should be found bad request when datetime is null value`() {
        mockMvc.perform(
            post("/api/wallet/deposit").content(
                """{
                    "datetime": "",
                    "amount": 1.1
               }"""
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{ "code" : 400, "message" : "datetime in request is required or not valid format."}"""))
    }

    @Test
    fun `should be found bad request when datetime field is missing`() {
        mockMvc.perform(
            post("/api/wallet/deposit").content(
                """{
                    "amount": 1.1
               }"""
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{ "code" : 400, "message" : "datetime in request is required or not valid format."}"""))
    }

    @Test
    fun `should be found bad request when amount is null value`() {
        mockMvc.perform(
            post("/api/wallet/deposit").content(
                """{
                    "datetime": "2019-10-05T14:48:01+01:00",
                    "amount": null
               }"""
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{ "code" : 400, "message" : "amount in request is required or not valid format."}"""))
    }

    @Test
    fun `should be found bad request when amount is less than zero value`() {
        mockMvc.perform(
            post("/api/wallet/deposit").content(
                """{
                    "datetime": "2019-10-05T14:48:01+01:00",
                    "amount": -10.0
               }"""
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{ "code" : 400, "message" : "[amount is The decimal value can not be less than 0.0]"}"""))
    }

    @Test
    fun `should be found bad request when amount is more than 9999999999_99999 value`() {
        mockMvc.perform(
            post("/api/wallet/deposit").content(
                """{
                    "datetime": "2019-10-05T14:48:01+01:00",
                    "amount": 19999999999.99999
               }"""
            )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{ "code" : 400, "message" : "[amount is The decimal value can not be more than 9999999999.99999]"}"""))
    }

    @Test
    fun `should be repsonse internal server error when caught something exception`(){
        every { saveWalletTransactionsPublisher.createTransaction(any()) } throws IllegalAccessException()
        mockMvc.perform(
            post("/api/wallet/deposit")
                .content(
                    """{
                    "datetime": "2019-10-05T14:48:01+01:00",
                    "amount": 1.1
               }"""
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
            .andExpect(content().json("""{ "code" : ${HttpStatus.INTERNAL_SERVER_ERROR.value()}, "message" : "Internal server error"}"""))
    }
}