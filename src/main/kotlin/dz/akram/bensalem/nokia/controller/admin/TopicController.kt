package dz.akram.bensalem.nokia.controller.admin

import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.domain.admin.entity.Topic
import dz.akram.bensalem.nokia.service.AdminService
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/api/admin/topic")
@AllArgsConstructor
class TopicController {

    @Autowired
    lateinit var adminService: AdminService

    @GetMapping("/all/{tenant}/{namespace}")
    fun listTopics(@PathVariable tenant: Tenant, @PathVariable namespace: String) : List<Topic> {
        val nameSpace = NameSpace("$tenant/$namespace")
        return adminService.listTopics(nameSpace)
    }

    @PostMapping("/create/{tenant}/{namespace}")
    fun createTopic(
        @PathVariable tenant: Tenant,
        @PathVariable namespace: String,
        @RequestParam name: String
    ) : String {
        val topic = Topic("$tenant/$namespace/$name")
        return adminService.createTopic(topic)
    }

    @DeleteMapping("/delete/{tenant}/{namespace}")
    fun deleteTopic(
        @PathVariable tenant: Tenant,
        @PathVariable namespace: String,
        @RequestParam name: String
    ) : String {
        val topic = Topic("$tenant/$namespace/$name")
        return adminService.deleteTopic(topic)
    }

    @GetMapping("/exists/{tenant}/{namespace}")
    fun checkTopicExists(
        @PathVariable tenant: Tenant,
        @PathVariable namespace: String,
        @RequestParam name: String
    ) : String {
        val topic = Topic("$tenant/$namespace/$name")
        return adminService.checkTopicIfExists(topic)
    }

}