package com.assignment.proa.weather

import com.assignment.proa.weather.controllers.WeatherStationController
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class WeatherApplication {
	@Autowired
	private lateinit var wsController: WeatherStationController
	private val logger = LoggerFactory.getLogger(WeatherApplication::class.java)

	@Bean
	fun initDb(): CommandLineRunner {
		return CommandLineRunner {
			logger.info("Importing data from CSV")
			wsController.importFromCsv()
			logger.info("Done")
		}
	}
}

fun main(args: Array<String>) {
	runApplication<WeatherApplication>(*args)
}
