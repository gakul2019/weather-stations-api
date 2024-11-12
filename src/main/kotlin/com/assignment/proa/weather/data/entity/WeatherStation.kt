package com.assignment.proa.weather.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
data class WeatherStation(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int,
    @Column(name="ws_name")
    val wsName: String,
    @Column
    val site: String,
    @Column
    val portfolio: String,
    @Column
    val state: String,
    @Column
    val latitude: Double,
    @Column
    val longitude: Double,
    @ManyToOne
    var variable: Variable? = null
)