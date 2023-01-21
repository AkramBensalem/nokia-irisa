package dz.akram.bensalem.nokia.domain.admin.entity

@JvmInline
value class Cluster(private val value: String): BaseEntity {

    override val isValid: Boolean
        get() = value.matches(Regex("^[a-zA-Z][a-zA-Z0-9]*\$"))

    override val name: String
        get() = value

    override fun toString(): String {
        return name
    }
}