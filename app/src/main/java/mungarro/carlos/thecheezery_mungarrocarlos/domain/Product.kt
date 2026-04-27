package mungarro.carlos.thecheezery_mungarrocarlos.domain

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Float,
    val image: String? = null,
    val description: String? = null,
    val type: String
)