package me.assignment.anymind.wallet.features.history.controller

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class HistoryControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var historyController: HistoryController

    @BeforeEach
    fun setUp() {
        historyController = HistoryController()
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build()
    }

    @AfterEach
    fun tearDown() {

    }

    @Test
    fun `history with valid request should be successfully`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/history?startDatetime=2011-10-05T10:48:01+00:00&endDatetime=2011-10-05T18:48:02+00:00"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should be bad request when parameter "startDatetime" is empty`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/history?startDatetime=&endDatetime=2011-10-05T18:48:02+00:00"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should be bad request when parameter "startDatetime" is invalid format date`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/history?startDatetime=2011-10-05&endDatetime=2011-10-05T18:48:02+00:00"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should be bad request when parameter "endDatetime" is empty`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/history?startDatetime=2011-10-05T10:48:01+00:00&endDatetime="))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should be bad request when parameter "endDatetime" is invalid format date`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/wallet/history?startDatetime=2011-10-05T10:48:01+00:00&endDatetime=2011-10-05"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}