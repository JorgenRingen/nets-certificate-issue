package com.example.netserror

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.lang.invoke.MethodHandles
import java.net.URI

@SpringBootApplication
class NetsErrorApplication {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    companion object {
        private const val URI_TEST = "https://test.epayment.nets.eu/Netaxept/Query.aspx"
        private const val URI_PROD = "https://epayment.nets.eu/Netaxept/Query.aspx"
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().build()
    }

    @Bean
    fun commandLineRunner(restTemplate: RestTemplate): CommandLineRunner {
        return CommandLineRunner {

            // Call test environment
            try {
                val response = restTemplate.getForEntity(buildUri(URI_TEST), String::class.java)
                logger.info("Response from test: $response")
            } catch (e: Exception) {
                logger.error("Error when calling test", e)
            }


            // Call prod environment
            try {
                val response = restTemplate.getForEntity(buildUri(URI_PROD), String::class.java)
                logger.info("Response from prod: $response")
            } catch (e: Exception) {
                logger.error("Error when calling prod", e)
            }
        }
    }

    private fun buildUri(uri: String): URI {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(uri)
        uriBuilder.queryParam("merchantId", "random")
        uriBuilder.queryParam("token", "random")
        uriBuilder.queryParam("transactionId", "random")
        return uriBuilder.build(true).toUri()
    }
}

fun main(args: Array<String>) {
    runApplication<NetsErrorApplication>(*args)
}
