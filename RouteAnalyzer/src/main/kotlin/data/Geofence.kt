package com.routeranalyzer.data

data class Geofence(
    val point: Waypoint,
    val geofenceRadiusKm: Double
) {
    fun isInRadius(point: Waypoint): Boolean {
        val distance = this.point.calculateDistance(point)
        return distance < this.geofenceRadiusKm
    }
}