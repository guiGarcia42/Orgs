package br.com.alura.orgs.extensions

import android.content.Context
import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load

object ImageLoaderSingleton {
    private var instance: ImageLoader? = null

    // Aqui nós retornamos só a instancia do imageLoader que
    // criamos abaixo, assim não precisando cria-lo toda vez
    fun getInstance(context: Context): ImageLoader {
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    instance = criarImageLoader(context)
                }
            }
        }
        return instance!!
    }

    // Aqui criamos o ImageLoader
    private fun criarImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                // Aqui configuramos o decoder pra adicionar o GIF
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()
    }
}
fun ImageView.tentaCarregarImagem(url: String? = null, imageLoader: ImageLoader? = null){

    if(imageLoader != null){
        load(url, imageLoader) {
            fallback(br.com.alura.orgs.R.drawable.erro) //No caso de retornar nulo
            error(br.com.alura.orgs.R.drawable.erro) //No caso de retornar imagem inválida
            placeholder(br.com.alura.orgs.R.drawable.placeholder) //Imagem de loading (pode ser um GIF tb)
        }
    } else {
        load(url) {
            fallback(br.com.alura.orgs.R.drawable.erro) //No caso de retornar nulo
            error(br.com.alura.orgs.R.drawable.erro) //No caso de retornar imagem inválida
            placeholder(br.com.alura.orgs.R.drawable.placeholder) //Imagem de loading (pode ser um GIF tb)
        }
    }
}

