package dz.akram.bensalem.nokia.controller.admin

import dz.akram.bensalem.nokia.domain.admin.entity.Cluster
import dz.akram.bensalem.nokia.domain.entity.Host
import dz.akram.bensalem.nokia.domain.entity.Port
import dz.akram.bensalem.nokia.service.AdminService
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/api/admin/cluster")
@AllArgsConstructor
class ClusterController {

    @Autowired
    lateinit var adminService: AdminService

    // list all clusters
    @GetMapping("/all")
    fun listClusters(): List<Cluster> {
        return adminService.listClusters()
    }

    // create a cluster
    @PostMapping("/create")
    fun createCluster(
        @RequestParam("name") name: String,
        @RequestParam("url", required = false, defaultValue = "127.0.0.1") url: Host,
        @RequestParam("port", required = true, defaultValue = "8080") port: Port
    ) : String {
        return adminService.createCluster(name, url, port)
    }

    // delete a cluster
    @DeleteMapping("/delete")
    fun deleteCluster(
        @RequestParam("name") name: String
    ) : String {
        return adminService.deleteCluster(name)
    }

    // check if a cluster exists
    @GetMapping("/exists")
    fun checkClusterIfExists(
        @RequestParam("name") name: String
    ) : String {
        return adminService.checkClusterIfExists(name)
    }
}