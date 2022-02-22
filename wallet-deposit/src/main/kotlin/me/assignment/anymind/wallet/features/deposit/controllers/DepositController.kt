package me.assignment.anymind.wallet.features.deposit.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/wallet")
class DepositController {
    @PostMapping("/deposit", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun deposit(): ResponseEntity<String> {
        return ResponseEntity.ok("""{ "code" : 0, "message" : "success"}""")
    }
}