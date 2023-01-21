package dz.akram.bensalem.nokia.domain.admin.entity

@JvmInline
value class NameSpace(private val value: String) : BaseEntity {

   override val isValid : Boolean
        get() = value.split("/").size == 2
                && value.matches(Regex("^[a-zA-Z][a-zA-Z0-9]*\$"))

    override val name: String
        get() = value.split("/").last()

    val tenant: Tenant
        get() = Tenant(value.split("/").first())

    override fun toString(): String {
        return name
    }

}