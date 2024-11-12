package com.assignment.proa.weather.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Variable(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @Column
    val name: String,
    @Column
    val unit: String,
    @Column
    val longName:String,
    @OneToOne
    var weatherData: WeatherData? = null
)
