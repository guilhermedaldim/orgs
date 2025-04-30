package com.example.orgs.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.orgs.databinding.FormularioImagemBinding
import com.example.orgs.extensions.carregarImagem

class FormularioImagemDialog(private val context: Context) {

    fun showDialog(
        urlPadrao: String? = null,
        callbackSuccess: (imagem: String) -> Unit,
    ) {
       FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply { // Apply função utilizada pra unir o binding
            urlPadrao?.let { // Função utilizada para verificar nulidade e chamar o código interno se null
                formularioImagemImageview.carregarImagem(it)
                formularioImagemUrl.setText(it)
            }

           formularioImagemBotao.setOnClickListener {
               val url = formularioImagemUrl.text.toString()
               formularioImagemImageview.carregarImagem(url)
           }

           AlertDialog.Builder(context)
               .setView(root)
               .setPositiveButton("Confirmar") { _, _ ->
                   val url = formularioImagemUrl.text.toString()
                   callbackSuccess(url)
               }
               .setNegativeButton("Cancelar") { _, _ ->

               }
               .show()
        }
    }
}