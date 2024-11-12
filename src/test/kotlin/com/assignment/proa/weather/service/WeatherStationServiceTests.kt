package com.assignment.proa.weather.service

import com.assignment.proa.weather.data.entity.WeatherStation
import com.assignment.proa.weather.data.repository.VariablesRepository
import com.assignment.proa.weather.data.repository.WeatherDataRepository
import com.assignment.proa.weather.data.repository.WeatherStationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest(classes = [
    WeatherStationRepository::class,
    WeatherDataRepository::class,
    VariablesRepository::class,
    WeatherStationService::class
])
@ActiveProfiles("test")
class WeatherStationServiceTests {
    @MockBean
    private lateinit var repository: WeatherStationRepository
    @MockBean
    private lateinit var weatherDataRepository: WeatherDataRepository
    @MockBean
    private lateinit var variablesRepository: VariablesRepository
    @Autowired
    private lateinit var service: WeatherStationService

    @Test
    fun `should import list of stations from csv`(){
        `when`(repository.save(any())).thenReturn(
            WeatherStation(
            id=1, wsName = "WS 1", site = "22 Rawson Place","Some company", "NSW",123.22, 324.33
        )
        )
        service.importAllWeatherStations("src/test/resources/proa_sample_data/weather_stations.csv")

        verify(repository, times(2)).save(any(WeatherStation::class.java))
    }

    @Test
    fun `should not import duplicate weather stations`(){
        `when`(repository.existsById(1)).thenReturn(true)
        `when`(repository.existsById(2)).thenReturn(false)
        `when`(repository.save(any())).thenReturn(
            WeatherStation(
                id=2, wsName = "Bungala 1 West", site = "Bungala 1 Solar Farm","Enel Green Power", "SA",-32.430536, 137.846245
            )
        )
        `when`(repository.findById(any())).thenReturn(
            Optional.of(WeatherStation(
                id=1, wsName = "Cohuna North", site = "Cohuna Solar Farm","Enel Green Power", "VIC",-32.430536, 137.846245
            ))
        )
        service.importAllWeatherStations("src/test/resources/proa_sample_data/weather_stations.csv")
        verify(repository, times(1)).save(WeatherStation(
            id=2, wsName = "Bungala 1 West", site = "Bungala 1 Solar Farm","Enel Green Power", "SA",-32.430536, 137.846245
        ))
    }

    @Test
    fun `should return all stations with findAll`(){
        `when`(repository.findAll()).thenReturn(
            listOf(
                WeatherStation(
                    id=1, wsName = "Cohuna North", site = "Cohuna Solar Farm","Enel Green Power", "VIC",-32.430536, 137.846245
                ),
                WeatherStation(
                    id=2, wsName = "Bungala 1 West", site = "Bungala 1 Solar Farm","Enel Green Power", "SA",-32.430536, 137.846245
                )
            )
        )

        val stations = service.findAllWeatherStations()
        assertEquals(2, stations.size)
    }

    @Test
    fun `should return specific station details with getStation`(){
        `when`(repository.findById(any())).thenReturn(
                Optional.of(WeatherStation(
                    id=1, wsName = "Cohuna North", site = "Cohuna Solar Farm","Enel Green Power", "VIC",-32.430536, 137.846245
                )))
        `when`(repository.existsById(any())).thenReturn(true)
        val station = service.getWeatherStation(1)
        assertEquals("VIC", station.state)
    }
    @Test
    fun `should throw error for invalid station id`(){
        `when`(repository.existsById(any())).thenReturn(false)

        assertThrows<ResponseStatusException>(
            "Requested ID does not match a weather station"
        ) { service.getWeatherStation(543) }
    }
}