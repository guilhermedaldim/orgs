package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.carregarImagem
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).showDialog(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.carregarImagem(url)
            }
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        val produtosDao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produto = criarProduto()

            val dao = produtosDao
            dao.adicionar(produto)

            finish() //Fecha a tela de adicionar produto e volta para a lista
        }
    }

    private fun criarProduto(): Produto {
        val inputNome = binding.activityFormularioProdutoNome
        val nome = inputNome.text.toString()

        val inputDescricao = binding.activityFormularioProdutoDescricao
        val descricao = inputDescricao.text.toString()

        val inputValor = binding.activityFormularioProdutoValor
        val valorString = inputValor.text.toString()
        val valor = if (valorString.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorString)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
        )
    }
}