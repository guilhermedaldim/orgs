package com.example.orgs.dao

import com.example.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Salada de Frutas",
                descricao = "Laranja, maçãs e uva",
                valor = BigDecimal("19.89")
            ),
        )
    }

    fun adicionar(produto: Produto) {
        produtos.add(produto)
    }

    fun buscar(): List<Produto> {
        return produtos.toList()
    }

}