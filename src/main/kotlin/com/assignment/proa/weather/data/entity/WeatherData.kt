package com.assignment.proa.weather.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
data class WeatherData (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @Column
    val timestamp: LocalDateTime,
    @Column
    val airtInst: Float? = null,
    @Column
    val ghiInst: Float? = null,
    @Column
    val wsAvg: Float? = null,
    @Column
    val wdAvg: Float? = null,
    @Column
    val avgAm2: Float? = null,
    @Column
    val avgAirTemp: Float? = null,
    @Column
    val avgWm2: Float? = null
)