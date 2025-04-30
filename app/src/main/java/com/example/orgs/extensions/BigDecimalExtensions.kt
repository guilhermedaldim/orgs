package com.example.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.formatarParaReal(valor: BigDecimal): String? {
    val valorReal = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valor)
    return valorReal
}