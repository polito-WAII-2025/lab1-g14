package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class OutputJson(
    val maxDistanceFromStart: MaxDistanceFromStart,
    val mostFrequentedArea: MostFrequentedArea,
    val waypointsOutsideGeofence: WaypointsOutsideGeofence
)

@Serializable
data class MaxDistanceFromStart(val waypoint: Waypoint, val distanceKm: Double)

@Serializable
data class MostFrequentedArea(val centralWaypoint: Waypoint, val areaRadiusKm: Double, val entriesCount: Int)

@Serializable
data class WaypointsOutsideGeofence(
    val centralWaypoint: Waypoint,
    val areaRadiusKm: Double,
    val count: Int,
    val waypoints: List<Waypoint>
)