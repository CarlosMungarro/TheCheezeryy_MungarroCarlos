package mungarro.carlos.thecheezery_mungarrocarlos.domain

data class Combo(
    val id: Int = 0,
    val name: String,
    val price: Float,
    val products: List<Product> = emptyList()
)