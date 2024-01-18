package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import br.com.alura.orgs.dao.ProdutosDAO
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDAO()
    private val adapter = ListaProdutosAdapter(this, dao.buscaTodos())

    //Inflando o layoutXML nós podemos acessar os componentes
    // Podemos tirar ela do onCreate e transformar em uma lazy property
    // Assim podendo acessa-la em outros métodos da classe
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    // Sobreescrevemos o método que é executado quando a activity é CRIADA
    // Dessa forma conseguimos implementar configurações no momento da criação da Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Precisamos vincular a View do ViewBinding com a activity
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "orgs.db"
        ).allowMainThreadQueries()
            .build()
        val produtoDao = db.produtoDao()
        produtoDao.salva(
            Produto(
                nome = "teste nome 1",
                descricao = "teste desc 1",
                valor = BigDecimal("10.0")
            )
        )

    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener() {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        // Chamamos a recyclerView na activity e para passar os dados precisamos de um Adapter
        val recyclerView = binding.activityListaProdutosRecyclerView
        // Passamos o context e uma lista de Produtos, podemos puxar isso de uma API na vida real
        recyclerView.adapter = adapter
        //Setando o layout como linear
        recyclerView.layoutManager = LinearLayoutManager(this)

        // implementação do listener para abrir a Activity de detalhes do produto
        // com o produto clicado
        adapter.quandoClicaNoItem = {
            val intent = Intent(this, DetalhesProdutoActivity::class.java)
                .apply {
                    // envio do produto por meio do extra
                    putExtra(CHAVE_PRODUTO, it)
                }
            startActivity(intent)
        }
    }

}