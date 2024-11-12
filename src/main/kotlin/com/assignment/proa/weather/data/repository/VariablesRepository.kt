package com.assignment.proa.weather.data.repository

import com.assignment.proa.weather.data.entity.Variable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VariablesRepository: JpaRepository<Variable, Int> {
}