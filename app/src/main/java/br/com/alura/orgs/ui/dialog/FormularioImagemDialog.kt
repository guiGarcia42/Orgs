package br.com.alura.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.orgs.databinding.FormularioImagemBinding
import br.com.alura.orgs.extensions.ImageLoaderSingleton
import br.com.alura.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {

    private val imageLoader = ImageLoaderSingleton.getInstance(context)

    // Podemos passar uma função como argumento
    fun mostra(
        urlPadrao: String? = null,
        quandoImagemCarregada: (imagem: String) -> Unit
    ) {
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {
            // Preenchendo no caso de ja ter uma url cadastrada
            urlPadrao?.let {
                formularioImagemImageview.tentaCarregarImagem(it, imageLoader)
                formularioImagemUrl.setText(it)
            }

            formularioImagemBotaoCarregar.setOnClickListener {
                val url = formularioImagemUrl.text.toString()
                formularioImagemImageview.tentaCarregarImagem(url, imageLoader)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = formularioImagemUrl.text.toString()
                    quandoImagemCarregada(url)
                    // Agora podemos usar o codigo que quiser quando a função for chamada
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }


}