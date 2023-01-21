package dz.akram.bensalem.nokia.controller.admin

import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.service.AdminService
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/api/admin/namespace")
@AllArgsConstructor
class NameSpaceController {

    @Autowired
    lateinit var adminService: AdminService

    @GetMapping("/all/{tenant}")
    fun listNameSpaces(@PathVariable tenant: Tenant) = adminService.listNamespaces(tenant)

    @PostMapping("/create/{tenant}")
    fun createNameSpace(
        @PathVariable tenant: Tenant,
        @RequestParam name: String
    ) : String {
        val nameSpace = NameSpace("$tenant/$name")
        return adminService.createNamespace(nameSpace)
    }

    @DeleteMapping("/delete/{tenant}")
    fun deleteNameSpace(
        @PathVariable tenant: Tenant,
        @RequestParam name: String
    ) : String {
        val nameSpace = NameSpace("$tenant/$name")
        return adminService.deleteNamespace(nameSpace)
    }

    @GetMapping("/exists/{tenant}")
    fun checkNameSpaceExists(
        @PathVariable tenant: Tenant,
        @RequestParam name: String
    ) : String {
        val nameSpace = NameSpace("$tenant/$name")
        return adminService.checkNamespaceIfExists(nameSpace)
    }

}