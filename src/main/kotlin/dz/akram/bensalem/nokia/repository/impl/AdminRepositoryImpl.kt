package dz.akram.bensalem.nokia.repository.impl

import dz.akram.bensalem.nokia.domain.admin.ApachePulsar
import dz.akram.bensalem.nokia.domain.admin.ApachePulsar.Companion.clusterData
import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.domain.admin.entity.Topic
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port
import dz.akram.bensalem.nokia.repository.AdminRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class AdminRepositoryImpl : AdminRepository {

    @Autowired
    private lateinit var admin: ApachePulsar

    /** Clusters **/

    override fun listClusters() = admin.listClusters()

    override fun createCluster(name: String, host: Host, port: Port) =
        admin.createCluster(name, ::clusterData)(host, port)

    override fun deleteCluster(name: String) = admin.deleteCluster(name)

    override fun checkClusterIfExists(name: String) = admin.checkClusterIfExists(name)

    /** Tenants **/

    override fun listTenants(): List<Tenant> = admin.listTenants()

    override fun createTenant(name: Tenant) = admin.createTenant(name)

    override fun deleteTenant(name: Tenant) = admin.deleteTenant(name)

    override fun checkTenantIfExists(name: Tenant) = admin.checkTenantIfExists(name)

    /** Namespaces **/

    override fun listNamespaces(tenant: Tenant) = admin.listNamespaces(tenant)

    override fun createNamespace(namespace: NameSpace) = admin.createNamespace(namespace)

    override fun deleteNamespace(namespace: NameSpace) = admin.deleteNamespace(namespace)


    override fun checkNamespaceIfExists(namespace: NameSpace) = admin.checkNamespaceIfExists(namespace)


    /** Topics **/

    override fun listTopics(namespace: NameSpace) = admin.listTopics(namespace)

    override fun createTopic(topic: Topic) = admin.createTopic(topic)

    override fun deleteTopic(topic: Topic) = admin.deleteTopic(topic)

    override fun checkTopicIfExists(topic: Topic) = admin.checkTopicIfExists(topic)


}