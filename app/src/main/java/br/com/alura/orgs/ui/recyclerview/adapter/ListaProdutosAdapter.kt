package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>,
    // declaração da função para o listener do adapter
    var quandoClicaNoItem: (produto: Produto) -> Unit = {}) :
    RecyclerView.
    Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

    //Alteramos o viewHolder para receber o binding e vinculamos a viewBinding a nossa ViewHolder
    // utilização do inner na classe interna para acessar membros da classe superior
    // nesse caso, a utilização da variável quandoClicaNoItem
    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Considerando que o ViewHolder modifica de valor com base na posição
        // é necessário o uso de properties mutáveis, para evitar nullables
        // utilizamos o lateinit, properties que podem ser inicializar depois
        private lateinit var produto: Produto

        init {
            // implementação do listener do adapter
            itemView.setOnClickListener {
                // verificação da existência de valores em property lateinit
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            binding.produtoItemNome.text = produto.nome
            binding.produtoItemDescricao.text = produto.descricao
            binding.produtoItemValor.text = produto.valor.formataParaMoedaBrasileira()

            // Aqui podemos definir se a imagem vai estar visivel ou não.
            (if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }).also { binding.produtoItemImagem.visibility = it }

            binding.produtoItemImagem.tentaCarregarImagem(produto.imagem)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Aqui inflamos o layoutXMl para poder usar os componentes
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size

}
