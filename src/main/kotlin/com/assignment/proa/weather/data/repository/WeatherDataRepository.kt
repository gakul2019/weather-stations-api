package com.assignment.proa.weather.data.repository

import com.assignment.proa.weather.data.entity.WeatherData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WeatherDataRepository: JpaRepository<WeatherData, Int> {
}