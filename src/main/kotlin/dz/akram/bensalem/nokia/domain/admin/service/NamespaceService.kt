package dz.akram.bensalem.nokia.domain.admin.service

import arrow.core.Either
import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant

interface NamespaceService {

    fun listNamespaces(tenant : Tenant): List<NameSpace>
    fun createNamespace(namespace: NameSpace): Either<String, Boolean>
    fun deleteNamespace(namespace: NameSpace): Either<String, Boolean>
    fun checkNamespaceIfExists(namespace: NameSpace): Either<String, Boolean>

}