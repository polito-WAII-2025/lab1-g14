package org.example.service

import kotlinx.serialization.json.Json
import org.example.model.*
import java.io.File

fun saveResultsToJson(
    farthestWaypoint: Waypoint,
    maxDistance: Double,
    mostFrequentedWaypoint: Waypoint,
    mostFrequentedCount: Int,
    waypointsOutside: List<Waypoint>,
    geofenceLat: Double,
    geofenceLon: Double,
    geofenceRadiusKm: Double
) {
    val outputJson = OutputJson(
        maxDistanceFromStart = MaxDistanceFromStart(farthestWaypoint, maxDistance),
        mostFrequentedArea = MostFrequentedArea(mostFrequentedWaypoint, 5.0, mostFrequentedCount),
        waypointsOutsideGeofence = WaypointsOutsideGeofence(
            centralWaypoint = Waypoint(0.0, geofenceLat, geofenceLon),
            areaRadiusKm = geofenceRadiusKm,
            count = waypointsOutside.size,
            waypoints = waypointsOutside
        )
    )

    val json = Json { prettyPrint = true }.encodeToString(OutputJson.serializer(), outputJson)
    File("output.json").writeText(json)
}