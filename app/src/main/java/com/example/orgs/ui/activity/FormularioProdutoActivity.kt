@file:Suppress("DEPRECATION")

package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.carregarImagem
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao by lazy {
        AppDatabase.instance(this).produtoDao()
    }
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

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

        carregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscarProduto()
    }

    private fun buscarProduto() {
        produtoDao.buscaPorId(produtoId)?.let {
            title = "Editar Produto"
            preencheCampos(it)
        }
    }

    private fun carregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioProdutoImagem.carregarImagem(produto.imagem)
        binding.activityFormularioProdutoNome.setText(produto.nome)
        binding.activityFormularioProdutoDescricao.setText(produto.descricao)
        binding.activityFormularioProdutoValor.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        botaoSalvar.setOnClickListener {
            val produto = criarProduto()
            produtoDao.salvar(produto)
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
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
        )
    }
}

