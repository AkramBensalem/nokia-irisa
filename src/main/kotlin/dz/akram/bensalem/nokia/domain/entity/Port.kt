package dz.akram.bensalem.nokia.domain.entity

@JvmInline
value class Port(private val value: Int){
    val isValid: Boolean
        get() = value in 1..65535

    val content: Int
        get() = if (isValid) value else 8080
}