package org.example.data

import kotlinx.serialization.Serializable
import kotlin.math.*

@Serializable
data class Waypoint(val timestamp : Double, val longitude : Double, val latitude : Double){

    companion object {
        fun haversineDistance(waypoint1: Waypoint, waypoint2: Waypoint, earthRadiusKm: Double): Double {
            val lat1 = Math.toRadians(waypoint1.latitude)
            val lon1 = Math.toRadians(waypoint1.longitude)
            val lat2 = Math.toRadians(waypoint2.latitude)
            val lon2 = Math.toRadians(waypoint2.longitude)

            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)

            val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return earthRadiusKm * c // Distance in km
        }
    }
}