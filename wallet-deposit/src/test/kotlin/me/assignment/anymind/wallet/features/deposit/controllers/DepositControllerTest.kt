package me.assignment.anymind.wallet.features.deposit.controllers

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

internal class DepositControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var depositController: DepositController

    @BeforeEach
    fun setUp() {
        depositController = DepositController()
        mockMvc = standaloneSetup(depositController).build()
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `deposit with valid request should be successfully`() {
        mockMvc.perform(post("/api/wallet/deposit")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().json("""{ "code" : 0, "message" : "success"}"""))
    }
}