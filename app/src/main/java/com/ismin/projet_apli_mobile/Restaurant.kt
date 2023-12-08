package com.ismin.projet_apli_mobile

import android.os.Parcelable
import java.io.Serializable

data class Restaurant(
    val nomoffre: String,
    val type: String? = null,
    val categorie: String? = null,
    val adresse1: String? = null,
    val adresse2: String? = null,
    val adresse3: String? = null,
    val codepostal: Int? = null,
    val commune: String? = null,
    val latitude: Double,
    val longitude: Double,
    val commtel: String? = null,
    val commmail: String? = null,
    val commweb: String? = null,
    val videosurl: String? = null,
    val plateformeurl: String? = null,
    val languesparleesacceuil: Array<String>? = null,
    val acceuilgroupe: String? = null,
    val tarifs: String? = null,
    val modepaiement: Array<String>? = null,
    val adressecomplete: String? = null,
    val urlimage: String? = null,
    var isFavori: Boolean = false
) : Serializable
