package me.assignment.anymind.wallet.features.deposit.models

import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotNull

data class DepositRequest(
    @field:NotNull
    val datetime: ZonedDateTime,
    @field:NotNull
    @field:DecimalMin(value = "0.0", inclusive = false, message = "The decimal value can not be less than 0.0")
    @field:DecimalMax(value = "9999999999.99999", inclusive = false, message = "The decimal value can not be more than 9999999999.99999")
    val amount: BigDecimal
)