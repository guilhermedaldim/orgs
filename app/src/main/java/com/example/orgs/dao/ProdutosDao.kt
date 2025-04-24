package com.example.orgs.dao

import com.example.orgs.model.Produto

class ProdutosDao {

    companion object {
        private val produtos = mutableListOf<Produto>()
    }

    fun adicionar(produto: Produto) {
        produtos.add(produto)
    }

    fun buscar() : List<Produto> {
        return produtos.toList()
    }

}