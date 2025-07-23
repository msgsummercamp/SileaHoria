package com.example.optional_airport_gap

import com.example.optional_airport_gap.parser.AirportParser
import com.example.optional_airport_gap.service.AirPortAPIService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class OptionalAirportGapApplication

fun main(args: Array<String>) {
	val context: ConfigurableApplicationContext = runApplication<OptionalAirportGapApplication>(*args)
	val services = context.getBean(AirPortAPIService::class.java)
	val airportParser = AirportParser()
	airportParser.parseAirportData(services.fetchApiResponse()?.data).forEach { println(it) }
}
