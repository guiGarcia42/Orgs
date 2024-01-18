package br.com.alura.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

//Mapeamento da entidade para o ROOM
@Entity
// implementação do Parcelable com o plugin parcelize
@Parcelize
data class Produto(
    // Definindo a primary key, se o valor for 0L vai gerar uma key
    // ou se receber outro valor é nLo caso de ser um produto existente
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagem: String? = null
) : Parcelable
