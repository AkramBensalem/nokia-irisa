package dz.akram.bensalem.nokia.domain.admin

import dz.akram.bensalem.nokia.domain.admin.service.ClusterService
import dz.akram.bensalem.nokia.domain.admin.service.TenantService
import arrow.core.*
import dz.akram.bensalem.nokia.domain.admin.entity.Cluster
import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.domain.admin.entity.Topic
import dz.akram.bensalem.nokia.domain.admin.service.NamespaceService
import dz.akram.bensalem.nokia.domain.admin.service.TopicService
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port
import org.apache.pulsar.client.admin.PulsarAdmin
import org.apache.pulsar.client.admin.PulsarAdminException
import org.apache.pulsar.common.policies.data.ClusterData
import org.apache.pulsar.common.policies.data.TenantInfo
import org.springframework.stereotype.Component
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class ApachePulsar(
    private val port: Int = PORT.content,
    private val host: String = HOST.content
) : AutoCloseable, ClusterService, TenantService, NamespaceService, TopicService {

    companion object {

         val PORT = Port(8080)
         val HOST = Host("localhost")

        fun clusterData(
            host: Host = HOST,
            port: Port
        ): ClusterData = ClusterData.builder().apply {
            serviceUrl("http://$host:$port")
            brokerServiceUrl("pulsar://$host:$port")
            serviceUrlTls("https://$host:$port")
            brokerServiceUrlTls("pulsar+ssl://$host:$port")
        }.build()
    }

    private val admin: Either<String, PulsarAdmin> by lazy {
        handleException(tag = "PulsarAdmin") { PulsarAdmin.builder().serviceHttpUrl("http://$host:$port").build() }
    }

    override fun close() {
        admin.map { it.close() }
    }

    private val config: TenantInfo
        get() = TenantInfo.builder().apply {
            adminRoles(Stream.of("admin").collect(Collectors.toSet()))
            allowedClusters(Stream.of("standalone").collect(Collectors.toSet()))
        }.build()

    private fun <T> handleException(
        tag: String,
        block: () -> T,
    ): Either<String, T> = try {
        block().right()
    } catch (e: PulsarAdminException) {
        when (e) {
            is PulsarAdminException.ConflictException -> "$tag already exists"
            is PulsarAdminException.NotFoundException -> "$tag not found"
            is PulsarAdminException.NotAllowedException -> "$tag not allowed"
            is PulsarAdminException.PreconditionFailedException -> "$tag precondition failed"
            is PulsarAdminException.ServerSideErrorException -> "$tag server side error"
            is PulsarAdminException.NotAuthorizedException -> "$tag unauthorized"
            is PulsarAdminException.TimeoutException -> "$tag timeout"
            is PulsarAdminException.HttpErrorException -> "$tag http error"
            is PulsarAdminException.ConnectException -> "$tag already closed"
            is PulsarAdminException.GettingAuthenticationDataException -> "$tag authentication error"
            else -> "Connection refused by the server\n${e.message}"
        }.left()
    } catch (e: Exception) {
        e.message.toString().left()
    }

    private fun <T> Either<String, PulsarAdmin>.useAdmin(tag: String, block: PulsarAdmin.() -> T): Either<String, T> =
        flatMap { admin ->
            handleException(tag) { block(admin) }
        }

    override fun createTenant(tenant: Tenant): Either<String, Boolean> = admin.useAdmin("Tenant") {
        tenants().createTenant(tenant.toString(), config)
        true
    }

    override fun listTenants(): List<Tenant> {
        return admin.useAdmin("Tenant") {
            tenants().tenants.map { Tenant(it) }
        }.getOrHandle { emptyList() }
    }

    override fun deleteTenant(tenant: Tenant): Either<String, Boolean> {
        return admin.useAdmin("Tenant") {
            tenants().deleteTenant(tenant.toString())
            true
        }
    }

    override fun checkTenantIfExists(tenant: Tenant): Either<String, Boolean> {
        return admin.useAdmin("Tenant") {
            tenants().tenants.any { it == tenant.toString() }
        }
    }

    override fun createCluster(
        name: String,
        data: (host: Host, port: Port) -> ClusterData
    ): (host: Host, port : Port) -> Either<String, Boolean> {
        return { host, port ->
            admin.useAdmin("Cluster") {
                clusters().createCluster(name, data(host, port))
                true
            }
        }
}

    override fun listClusters(): List<Cluster> = admin.useAdmin("Cluster") {
        clusters().clusters.map { Cluster(it) }
    }.getOrElse {
        emptyList()
    }

    override fun deleteCluster(name: String): Either<String, Boolean> {
        return admin.useAdmin("Cluster") {
            clusters().deleteCluster(name)
            true
        }
    }

    override fun checkClusterIfExists(name: String): Either<String, Boolean> {
        return admin.useAdmin("Cluster") {
            clusters().clusters.any { it == name }
        }
    }

    override fun listNamespaces(tenant: Tenant): List<NameSpace> {
        return admin.useAdmin("Namespace") {
            namespaces().getNamespaces(tenant.toString()).map { NameSpace(it) }
        }.getOrElse {
            emptyList()
        }
    }

    override fun createNamespace(namespace: NameSpace): Either<String, Boolean> {
        return admin.useAdmin("Namespace") {
            namespaces().createNamespace(namespace.toString())
            true
        }
    }

    override fun deleteNamespace(namespace: NameSpace): Either<String, Boolean> {
        return admin.useAdmin("Namespace") {
            namespaces().deleteNamespace(namespace.toString())
            true
        }
    }

    override fun checkNamespaceIfExists(namespace: NameSpace): Either<String, Boolean> {
        return admin.useAdmin("Namespace") {
            namespaces().getNamespaces(namespace.tenant.toString()).any { it == namespace.toString() }
        }
    }

    override fun listTopics(namespace: NameSpace): List<Topic> {
        return admin.useAdmin("Topic") {
            topics().getList("$namespace").map { Topic(it) }
        }.getOrElse {
            emptyList()
        }
    }

    override fun createTopic(topic: Topic): Either<String, Boolean> {
        return admin.useAdmin("Topic") {
            topics().createNonPartitionedTopic("$topic")
            true
        }
    }

    override fun deleteTopic(topic: Topic): Either<String, Boolean> {
        return admin.useAdmin("Topic") {
            topics().delete("$topic")
            true
        }
    }

    override fun checkTopicIfExists(topic: Topic): Either<String, Boolean> {
        return admin.useAdmin("Topic") {
            topics().getList("${topic.nameSpace}").any { it == "$topic" }
        }
    }

}