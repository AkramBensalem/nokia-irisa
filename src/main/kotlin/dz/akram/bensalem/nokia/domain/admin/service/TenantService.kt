package dz.akram.bensalem.nokia.domain.admin.service

import arrow.core.Either
import dz.akram.bensalem.nokia.domain.admin.entity.Tenant

interface TenantService {

    fun createTenant(tenant: Tenant): Either<String, Boolean>
    fun listTenants(): List<Tenant>
    fun deleteTenant(tenant: Tenant): Either<String, Boolean>
    fun checkTenantIfExists(tenant: Tenant): Either<String, Boolean>

}