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

@SpringBootApplication
class NetsErrorApplication {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    private val queryUri = "https://test.epayment.nets.eu/Netaxept/Query.aspx"

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().build()
    }

    @Bean
    fun commandLineRunner(restTemplate: RestTemplate): CommandLineRunner {
        return CommandLineRunner {

            val uriBuilder = UriComponentsBuilder.fromHttpUrl(queryUri)
            uriBuilder.queryParam("merchantId", "random")
            uriBuilder.queryParam("token", "random")
            uriBuilder.queryParam("transactionId", "random")


            // throws javax.net.ssl.SSLHandshakeException
            restTemplate.getForEntity(uriBuilder.build(true).toUri(), String::class.java)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<NetsErrorApplication>(*args)
}
