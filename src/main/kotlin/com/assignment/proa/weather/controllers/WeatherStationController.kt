package com.assignment.proa.weather.controllers

import com.assignment.proa.weather.data.entity.WeatherData
import com.assignment.proa.weather.data.entity.WeatherStation
import com.assignment.proa.weather.service.WeatherStationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ws")
class WeatherStationController(private val wsService: WeatherStationService) {

    @GetMapping("/all")
    fun getAllWeatherStations(): List<WeatherStation> {
        return wsService.findAllWeatherStations()
    }

    @GetMapping("/import")
    fun importFromCsv(){
        wsService.importAllWeatherStations("src/main/resources/static/proa_sample_data/weather_stations.csv")
        Thread.sleep(2000)
        wsService.importAllVariablesFromCsv("src/main/resources/static/proa_sample_data/variables.csv")
        Thread.sleep(2000)
        for (i in 1..10){
            wsService.importAllWeatherDataFromCsv("src/main/resources/static/proa_sample_data/data_$i.csv", i)
        }
    }

    @GetMapping("/{id}")
    fun getLatestMeasurementDataForStation(@PathVariable id: Int): WeatherData? {
        return wsService.getLatestWeatherStationData(id)
    }
}