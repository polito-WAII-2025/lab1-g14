package com.routeranalyzer.data

import com.routeranalyzer.Config
import kotlinx.serialization.Serializable
import org.gavaghan.geodesy.GeodeticCalculator
import org.gavaghan.geodesy.GlobalCoordinates

@Serializable
data class Waypoint(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Double? = null
) {
    companion object {
        private val calculator = GeodeticCalculator()
        private val ellipsoidRef = Config.REF_ELLIPSOID
    }

    private val position: GlobalCoordinates
        get() {
            return GlobalCoordinates(latitude, longitude)
        }

    fun calculateDistance(position: Waypoint): Double {
        return calculateDistance(position.position)
    }

    private fun calculateDistance(position: GlobalCoordinates): Double {
        val curve = calculator.calculateGeodeticCurve(ellipsoidRef, this.position, position)
        return curve.ellipsoidalDistance / 1000
    }
}