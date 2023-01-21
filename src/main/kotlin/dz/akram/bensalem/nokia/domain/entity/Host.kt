package dz.akram.bensalem.nokia.domain.entity

@JvmInline
value class Host(private val host: String){
    val isValid: Boolean
        get() = host.matches(Regex("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\$")) || host.matches(Regex("^(?:[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*\\.)+[a-zA-Z]{2,}\$"))

    val content: String
        get() = if (isValid) host else "localhost"
}