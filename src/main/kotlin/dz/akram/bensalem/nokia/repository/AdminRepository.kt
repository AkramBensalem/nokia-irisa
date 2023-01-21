package dz.akram.bensalem.nokia.repository

import arrow.core.Either
import dz.akram.bensalem.nokia.domain.admin.entity.Cluster
import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.domain.admin.entity.Topic
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port

interface AdminRepository {

    fun listClusters(): List<Cluster>
    fun createCluster(name: String, host: Host, port: Port):  Either<String, Boolean>
    fun deleteCluster(name: String):  Either<String, Boolean>
    fun checkClusterIfExists(name: String): Either<String, Boolean>

    fun listTenants(): List<Tenant>
    fun createTenant(name: Tenant): Either<String, Boolean>
    fun deleteTenant(name: Tenant): Either<String, Boolean>
    fun checkTenantIfExists(name: Tenant): Either<String, Boolean>

    fun listNamespaces(tenant : Tenant): List<NameSpace>
    fun createNamespace(namespace: NameSpace): Either<String, Boolean>
    fun deleteNamespace(namespace: NameSpace): Either<String, Boolean>
    fun checkNamespaceIfExists(namespace: NameSpace): Either<String, Boolean>

    fun listTopics(namespace : NameSpace): List<Topic>
    fun createTopic(topic : Topic): Either<String, Boolean>
    fun deleteTopic(topic : Topic): Either<String, Boolean>
    fun checkTopicIfExists(topic : Topic): Either<String, Boolean>

}