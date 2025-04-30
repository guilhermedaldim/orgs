package com.example.orgs.extensions

import android.widget.ImageView
import coil.load
import com.example.orgs.R

fun ImageView.carregarImagem(url: String? = null) {
    load(url) {
        crossfade(true)
        fallback(R.drawable.imagem_padrao)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}
