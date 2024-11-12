package com.assignment.proa.weather

import com.assignment.proa.weather.controllers.WeatherStationController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class WeatherApplicationTests {

	@MockBean
	private lateinit var wsController: WeatherStationController
	@Test
	fun contextLoads() {
	}

	@Test
	fun `should import list of weather stations at bootrun`() {
        // when the test is run, the context is pre-loaded by Spring boot and commandline runner is already triggered
		verify(wsController).importFromCsv()
	}
}
