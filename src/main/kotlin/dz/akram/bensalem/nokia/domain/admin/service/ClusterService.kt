package dz.akram.bensalem.nokia.domain.admin.service

import arrow.core.Either
import dz.akram.bensalem.nokia.domain.admin.entity.Cluster
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port
import org.apache.pulsar.common.policies.data.ClusterData

interface ClusterService {
    fun createCluster(name: String, data : (Host, Port) -> ClusterData) : (Host , Port) -> Either<String, Boolean>
    fun listClusters(): List<Cluster>
    fun deleteCluster(name: String): Either<String, Boolean>
    fun checkClusterIfExists(name: String): Either<String, Boolean>
}