package com.example.orgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.extensions.carregarImagem
import com.example.orgs.extensions.formatarParaReal
import com.example.orgs.model.Produto

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList(),
    var callbackItem: (produto: Produto) -> Unit = {},
    var callbackEditar: (produto: Produto) -> Unit = {},
    var callbackDeletar: (produto: Produto) -> Unit = {},
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    callbackItem(produto)
                }
            }

            itemView.setOnLongClickListener {
                PopupMenu(context, itemView).apply {
                    menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
                    setOnMenuItemClickListener(this@ViewHolder)
                }.show()
                true
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            val valorEmReal = produto.valor.formatarParaReal(produto.valor)
            valor.text = valorEmReal

            binding.produtoItemImagem.visibility = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.produtoItemImagem.carregarImagem(produto.imagem)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when(it.itemId) {
                    R.id.menu_detalhes_produto_editar -> {
                        callbackEditar(produto)
                    }
                    R.id.menu_detalhes_produto_remover -> {
                        callbackDeletar(produto)
                    }
                }
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualizar(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
