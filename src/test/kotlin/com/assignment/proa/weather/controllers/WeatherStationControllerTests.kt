package com.assignment.proa.weather.controllers

import com.assignment.proa.weather.data.entity.WeatherData
import com.assignment.proa.weather.data.entity.WeatherStation
import com.assignment.proa.weather.service.WeatherStationService
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class WeatherStationControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: WeatherStationService

    @Test
    fun `should be able to retrieve all weather stations`(){
        `when`(service.findAllWeatherStations()).thenReturn(listOf(
            WeatherStation(id =1, wsName ="Cohuna North", site ="Cohuna Solar Farm", portfolio ="Enel Green Power", state ="VIC", latitude =-35.882763, longitude =144.21721),
            WeatherStation(id =2, wsName ="Bungala 1 West", site ="Bungala 1 Solar Farm", portfolio ="Enel Green Power", state ="SA", latitude =-32.430534, longitude =137.84625)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/ws/all").contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].state", CoreMatchers.`is`("VIC")))
    }

    @Test
    fun `should retrieve measurements of specific station given an id that exists`() {
        `when`(service.getLatestWeatherStationData(123)).thenReturn(
            WeatherData(
                id=1,
                timestamp = LocalDateTime.now()
            )
        )

        mockMvc.perform(MockMvcRequestBuilders.get("/ws/123").contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)

    }
}