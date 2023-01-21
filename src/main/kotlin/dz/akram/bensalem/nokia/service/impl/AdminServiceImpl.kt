package dz.akram.bensalem.nokia.service.impl

import arrow.core.getOrHandle
import dz.akram.bensalem.nokia.domain.admin.entity.*
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port
import dz.akram.bensalem.nokia.repository.AdminRepository
import dz.akram.bensalem.nokia.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminServiceImpl : AdminService {

    @Autowired
    lateinit var adminRepository: AdminRepository

    override fun listClusters(): List<Cluster> {
        return adminRepository.listClusters()
    }

    private fun checkName(
        tag: String, name: BaseEntity, block: (BaseEntity) -> String
    ) = if (name.isValid) {
        block(name)
    } else {
        "Invalid $tag name"
    }

    override fun createCluster(name: String, host: Host, port: Port) = if (host.isValid && port.isValid) {
        adminRepository.createCluster(name, host, port).map {
                if (it) "Cluster $name created successfully"
                else "Cluster $name already exists"
            }.getOrHandle { it }
    } else {
        "Invalid host or port"
    }

    override fun deleteCluster(name: String) = adminRepository.deleteCluster(name).map {
            if (it) "Cluster $name deleted successfully"
            else "Cluster $name doesn't exist"
        }.getOrHandle { it }

    override fun checkClusterIfExists(name: String) = adminRepository.checkClusterIfExists(name).map {
        if (it) {
            "Cluster $name exists"
        } else {
            "Cluster $name doesn't exist"
        }
    }.getOrHandle { "Error: $it" }

    override fun listTenants() = adminRepository.listTenants()

    override fun createTenant(tenant: Tenant) = checkName("Tenant", tenant) {
        adminRepository.createTenant(tenant).map {
                if (it) "Tenant $tenant created successfully"
                else "Tenant $tenant already exists"
            }.getOrHandle { it }
    }

    override fun deleteTenant(tenant: Tenant) = checkName("Tenant", tenant) {
        adminRepository.deleteTenant(tenant).map {
                if (it) "Tenant $tenant deleted successfully"
                else "Tenant $tenant doesn't exist"
            }.getOrHandle { it }
    }

    override fun checkTenantIfExists(tenant: Tenant) = checkName("Tenant", tenant) {
        adminRepository.checkTenantIfExists(tenant).map {
                if (it) {
                    "Tenant $tenant exists"
                } else {
                    "Tenant $tenant doesn't exist"
                }
            }.getOrHandle { "Error: $it" }
    }

    override fun listNamespaces(tenant: Tenant) = adminRepository.listNamespaces(tenant)

    override fun createNamespace(namespace: NameSpace) = checkName("Namespace", namespace) {
        adminRepository.createNamespace(namespace).map {
                if (it) "Namespace ${namespace.name} created successfully"
                else "Namespace ${namespace.name} already exists"
            }.getOrHandle { it }
    }

    override fun deleteNamespace(namespace: NameSpace) = checkName("Namespace", namespace) {
        adminRepository.deleteNamespace(namespace).map {
                if (it) "Namespace ${namespace.name} deleted successfully"
                else "Namespace ${namespace.name} doesn't exist"
            }.getOrHandle { it }
    }

    override fun checkNamespaceIfExists(namespace: NameSpace) = checkName("Namespace", namespace) {
        adminRepository.checkNamespaceIfExists(namespace).map {
                if (it) {
                    "Namespace ${namespace.name} exists"
                } else {
                    "Namespace ${namespace.name} doesn't exist"
                }
            }.getOrHandle { "Error: $it" }
    }

    override fun listTopics(namespace: NameSpace) = adminRepository.listTopics(namespace)

    override fun createTopic(topic: Topic) = checkName("Topic", topic) {
        adminRepository.createTopic(topic).map {
                if (it) "Topic ${topic.name} created successfully"
                else "Topic ${topic.name} already exists"
            }.getOrHandle { it }
    }

    override fun deleteTopic(topic: Topic) = checkName("Topic", topic) {
        adminRepository.deleteTopic(topic).map {
                if (it) "Topic ${topic.name} deleted successfully"
                else "Topic ${topic.name} doesn't exist"
            }.getOrHandle { it }
    }

    override fun checkTopicIfExists(topic: Topic) = checkName("Topic", topic) {
        adminRepository.checkTopicIfExists(topic).map {
                if (it) {
                    "Topic ${topic.name} exists"
                } else {
                    "Topic ${topic.name} doesn't exist"
                }
            }.getOrHandle { "Error: $it" }
    }
}