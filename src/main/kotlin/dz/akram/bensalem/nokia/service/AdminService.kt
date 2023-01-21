package dz.akram.bensalem.nokia.service

import dz.akram.bensalem.nokia.domain.admin.entity.Cluster
import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.domain.admin.entity.Topic
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port

interface AdminService {

    fun listClusters(): List<Cluster>
    fun createCluster(name: String, host : Host, port : Port): String
    fun deleteCluster(name: String): String
    fun checkClusterIfExists(name: String): String

    fun listTenants(): List<Tenant>
    fun createTenant(tenant: Tenant): String
    fun deleteTenant(tenant: Tenant): String
    fun checkTenantIfExists(tenant: Tenant): String

    fun listNamespaces(tenant : Tenant): List<NameSpace>
    fun createNamespace(namespace : NameSpace): String
    fun deleteNamespace(namespace : NameSpace): String
    fun checkNamespaceIfExists(namespace : NameSpace): String

    fun listTopics(namespace : NameSpace): List<Topic>
    fun createTopic(topic : Topic): String
    fun deleteTopic(topic : Topic): String
    fun checkTopicIfExists(topic : Topic): String

}