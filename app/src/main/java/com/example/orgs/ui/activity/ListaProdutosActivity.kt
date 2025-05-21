package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter


class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val produtoDao = db.produtoDao()
        adapter.atualizar(produtoDao.buscarTodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListProdutosReciclerView
        recyclerView.adapter = adapter
        navegarParaDetalhes()
        editarItem()
        deletarItem()
    }

    private fun configuraFab() {
        val fab = binding.activityListProdutosFab
        fab.setOnClickListener {
            navegarParaFormulario()
        }
    }

    private fun navegarParaFormulario() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun navegarParaDetalhes() {
        adapter.callbackItem = {
            val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }
    }

    private fun editarItem() {
        adapter.callbackEditar = {}
    }

    private fun deletarItem() {
        adapter.callbackDeletar = {}
    }

}