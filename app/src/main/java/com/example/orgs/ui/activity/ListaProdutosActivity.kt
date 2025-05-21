package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.model.Produto
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter


class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDatabase.instance(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizar(produtoDao.buscarTodos())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtosOrdenado: List<Produto>? = when (item.itemId) {
            R.id.menu_lista_produtos_nome_asc ->
                produtoDao.buscaTodosOrdenadorPorNomeAsc()

            R.id.menu_lista_produtos_nome_desc ->
                produtoDao.buscaTodosOrdenadorPorNomeDesc()

            R.id.menu_lista_produtos_desc_asc ->
                produtoDao.buscaTodosOrdenadorPorDescricaoAsc()

            R.id.menu_lista_produtos_desc_desc ->
                produtoDao.buscaTodosOrdenadorPorDescricaoDesc()

            R.id.menu_lista_produtos_valor_asc ->
                produtoDao.buscaTodosOrdenadorPorValorAsc()

            R.id.menu_lista_produtos_valor_desc ->
                produtoDao.buscaTodosOrdenadorPorValorDesc()

            R.id.menu_lista_produtos_sem_ord ->
                produtoDao.buscarTodos()

            else -> null
        }
        produtosOrdenado?.let {
            adapter.atualizar(it)
        }

        return super.onOptionsItemSelected(item)
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
                putExtra(CHAVE_PRODUTO_ID, it.id)
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