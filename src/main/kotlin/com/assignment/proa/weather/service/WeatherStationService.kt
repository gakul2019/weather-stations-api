package com.assignment.proa.weather.service

import com.assignment.proa.weather.data.entity.Variable
import com.assignment.proa.weather.data.entity.WeatherData
import com.assignment.proa.weather.data.entity.WeatherStation
import com.assignment.proa.weather.data.repository.VariablesRepository
import com.assignment.proa.weather.data.repository.WeatherDataRepository
import com.assignment.proa.weather.data.repository.WeatherStationRepository
import com.opencsv.CSVReader
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.io.FileReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class WeatherStationService(
    private val wsRepository: WeatherStationRepository,
    private val wdRepository: WeatherDataRepository,
    private val variablesRepository: VariablesRepository
 ) {
    private val logger = LoggerFactory.getLogger(WeatherStationService::class.java)

    fun importAllWeatherStations(location: String) {
        val reader = CSVReader(FileReader(location))
        val data = reader.readAll()
        data.forEach { ws ->
            //skip the headings
            if (ws[0] != "id") {
                val id = ws[0].toInt()
                val saved = if(!wsRepository.existsById(id)) { // validate if exists
                    val station = WeatherStation(
                        id = id,
                        wsName = ws[1],
                        site = ws[2],
                        portfolio = ws[3],
                        state = ws[4],
                        latitude = ws[5].toDouble(),
                        longitude = ws[6].toDouble()
                    )
                    wsRepository.save(station)
                } else{
                    wsRepository.findById(id).get()
                }
                logger.info(saved.toString())
            }
        }
    }

    fun importAllVariablesFromCsv(location: String){
        val reader = CSVReader(FileReader(location))
        val data = reader.readAll()
        data.forEach { v ->
            //skip the headings
            if (v[0] != "var_id") {
                val wsId = v[1].toInt()
                val varId = v[0].toInt()
                val saved = if(!variablesRepository.existsById(varId)){
                    val variable = Variable(
                        id = varId,
                        name = v[2],
                        unit = v[3],
                        longName = v[4]
                    )
                    variablesRepository.save(variable)

                } else {
                    variablesRepository.findById(varId).get()
                }
                val ws = wsRepository.findById(wsId).get()
                ws.variable = saved
                wsRepository.save(ws)
                logger.info(saved.toString())
            }
        }
    }

    fun importAllWeatherDataFromCsv(location: String, wsId: Int){
        val reader = CSVReader(FileReader(location))
        val data = reader.readAll()
        val ws = wsRepository.findById(wsId).get()
        data.forEach { d ->
            val timestampStr = d.takeLast(1)[0]
            if ( timestampStr != "timestamp") { //skip the headings
                val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm:ss")
                val timestamp = LocalDateTime.parse(timestampStr, pattern)
                val saved: WeatherData
                val wData:WeatherData
                when(wsId) {
                    1-> {
                        wData = WeatherData(
                            timestamp = timestamp,
                            airtInst = d[0].toFloat(),
                            ghiInst = d[1].toFloat()
                        )
                    }
                    2 -> {
                        wData = WeatherData(
                            timestamp = timestamp,
                            avgWm2 = d[0].toFloat(),
                            avgAirTemp = d[1].toFloat()
                        )
                    }
                    3,9, 10 -> {
                        wData = WeatherData(
                            timestamp = timestamp,
                            ghiInst = d[0].toFloat(),
                            airtInst = d[1].toFloat()
                        )
                    }
                    4,5,6 -> {
                        wData = WeatherData(
                            timestamp = timestamp,
                            ghiInst = d[0].toFloat()
                        )
                    }
                    7,8 -> {
                        wData = WeatherData(
                            timestamp = timestamp,
                            wsAvg = d[0].toFloat(),
                            wdAvg = d[1].toFloat()
                        )
                    }
                    else ->{
                        wData = WeatherData(timestamp = timestamp)
                    }

                }
                saved = wdRepository.save(wData)
                ws.variable?.weatherData = saved
                variablesRepository.save(ws.variable!!)
                logger.info(saved.toString())
            }
        }
    }

    fun findAllWeatherStations() :List<WeatherStation>{
        return wsRepository.findAll()
    }

    fun getWeatherStation(id: Int): WeatherStation {
        if(wsRepository.existsById(id)) {
            return wsRepository.findById(id).orElseThrow()
        } else {
            throw ResponseStatusException(HttpStatusCode.valueOf(404),
                "Requested ID does not match a weather station")
        }
    }

    fun getLatestWeatherStationData(id: Int): WeatherData? {
        if(wsRepository.existsById(id)) {
            val station = wsRepository.findById(id).get()
            return station.variable?.weatherData
        }else {
            throw ResponseStatusException(HttpStatusCode.valueOf(404),
                "Requested ID does not match a weather station")
        }
    }
}