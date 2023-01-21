package dz.akram.bensalem.nokia.controller.admin

import dz.akram.bensalem.nokia.domain.admin.entity.Tenant
import dz.akram.bensalem.nokia.service.AdminService
import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/api/admin/tenant")
@AllArgsConstructor
class TenantController {

    @Autowired
    lateinit var adminService: AdminService

    // list all tenants
    @GetMapping("/all")
    fun listTenants() = adminService.listTenants()

    // create a tenant
    @PostMapping("/create")
    fun createTenant(@RequestParam name: Tenant) = adminService.createTenant(name)

    // delete a tenant
    @DeleteMapping("/delete")
    fun deleteTenant(@RequestParam name: Tenant) = adminService.deleteTenant(name)

    // check if a tenant exists
    @GetMapping("/exists")
    fun checkTenantIfExists(@RequestParam name: Tenant) = adminService.checkTenantIfExists(name)
}