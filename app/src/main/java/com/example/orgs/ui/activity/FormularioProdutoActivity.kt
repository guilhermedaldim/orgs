package com.example.orgs.ui.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = findViewById<Button>(R.id.activity_formulario_produto_botao_salvar)
        val produtosDao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produto = criarProduto()

            val dao = produtosDao
            dao.adicionar(produto)

            finish() //Fecha a tela de adicionar produto e volta para a lista
        }
    }

    private fun criarProduto(): Produto {
        val inputNome = findViewById<EditText>(R.id.activity_formulario_produto_nome)
        val nome = inputNome.text.toString()

        val inputDescricao = findViewById<EditText>(R.id.activity_formulario_produto_descricao)
        val descricao = inputDescricao.text.toString()

        val inputValor = findViewById<EditText>(R.id.activity_formulario_produto_valor)
        val valorString = inputValor.text.toString()
        val valor = if(valorString.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorString)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}