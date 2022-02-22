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
            MockMvcRequestBuilders.get("/api/history")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json("""{ "code" : 0, "message" : "success"}"""))
    }
}