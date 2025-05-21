package com.example.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.orgs.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscarTodos(): List<Produto>

    @Insert
    fun salvar(vararg produto: Produto)

    @Update
    fun atualizar(vararg produto: Produto)

    @Delete
    fun remover(vararg produto: Produto)

}