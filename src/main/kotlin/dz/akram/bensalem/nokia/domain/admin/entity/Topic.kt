package dz.akram.bensalem.nokia.domain.admin.entity

@JvmInline
value class Topic(private val value: String) : BaseEntity {

    override val name: String
        get() = value.split("/").last()

    override val isValid: Boolean
        get() = value.isNotBlank()
                && value.isNotEmpty()
                && value.split("/").size == 3
                && value.matches(Regex("^[a-zA-Z][a-zA-Z0-9]*\$"))

    val tenant: Tenant
        get() = Tenant(value.split("/").first())

    val nameSpace: NameSpace
        get() = NameSpace(  "$tenant/" + value.split("/")[1])


    override fun toString(): String {
        return value
    }

}