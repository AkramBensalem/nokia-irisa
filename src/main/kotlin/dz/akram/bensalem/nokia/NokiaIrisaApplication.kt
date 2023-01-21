package dz.akram.bensalem.nokia

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class NokiaIrisaApplication : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(NokiaIrisaApplication::class.java)

    override fun run(vararg args: String?) {
        logger.info("Apache Pulsar begin")
    }

}

fun main(args: Array<String>) {
    runApplication<NokiaIrisaApplication>(*args)
}


