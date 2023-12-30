package br.com.alura.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    // Sobreescrevemos o método que é executado quando a activity é CRIADA
    // Dessa forma conseguimos implementar configurações no momento da criação da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Chamamos a recyclerView na activity e para passar os dados precisamos de um Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        // Passamos o context e uma lista de Produtos, podemos puxar isso de uma API na vida real
        recyclerView.adapter = ListaProdutosAdapter(this, listOf(
            Produto(
                "teste 1",
                "teste descrição",
                BigDecimal("19.99")
            ),
            Produto(
                "teste 2",
                "teste descrição 2",
                BigDecimal("29.99")
            ),
            Produto(
                "teste 3",
                "teste descrição 3",
                BigDecimal("39.99")
            ),
        ))
        //Setando o layout como linear
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}