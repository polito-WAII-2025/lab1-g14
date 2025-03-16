package org.example.analyzer

import org.example.data.MaxDistanceResult
import org.example.data.MostFrequentedAreaResult
import org.example.data.Waypoint
import org.example.data.Waypoint.Companion.haversineDistance
import org.example.data.WaypointsOutsideGeofenceResult


fun maxDistanceFromStart(waypoints: List<Waypoint>, earthRadiusKm: Double) : MaxDistanceResult? {
    if (waypoints.isEmpty()) return null

    val startPoint = waypoints[0]
    var maxWaypoint = waypoints[0]  //for beginning, suppose that first waypoint is the farthest one

    var maxDistance = 0.0

    for (waypoint in waypoints) {
        val distance = haversineDistance(waypoint, startPoint, earthRadiusKm)
        if (distance > maxDistance) {
            maxWaypoint = waypoint
            maxDistance = distance
        }
    }

    return MaxDistanceResult(
        waypoint = maxWaypoint,
        distanceKm = maxDistance
    )
}

fun findMostFrequentedArea(waypoints: List<Waypoint>, radiusKm: Double = 5.0, earthRadiusKm: Double): MostFrequentedAreaResult? {
    if (waypoints.isEmpty()) return null

    val clusterCounts = mutableMapOf<Waypoint, Int>()

    for (waypoint in waypoints) {
        var foundCluster = false

        for (existingWaypoint in clusterCounts.keys) {
            val distance = haversineDistance(waypoint, existingWaypoint, earthRadiusKm)

            if (distance <= radiusKm) {
                clusterCounts[existingWaypoint] = clusterCounts[existingWaypoint]!! + 1
                foundCluster = true
                break
            }
        }

        if (!foundCluster) {
            clusterCounts[waypoint] = 1
        }
    }

    // Find the most frequented area (highest visited cluster)
    val mostFrequentArea = clusterCounts.maxByOrNull { it.value }

    return mostFrequentArea?.let {
        MostFrequentedAreaResult(
            centralWaypoint = it.key,
            areaRadiusKm = radiusKm,
            entriesCount = it.value
        )
    }
}

fun waypointsOutsideGeofence(waypoints: List<Waypoint>, geofenceLat: Double, geofenceLon: Double,
                             radius: Double, earthRadiusKm: Double): WaypointsOutsideGeofenceResult {
    val outWaypoints = mutableListOf<Waypoint>()
    val center = Waypoint(0.0, geofenceLat, geofenceLon)

    for (point in waypoints) {
        val distance = haversineDistance(center, point, earthRadiusKm)

        if (distance <= radius) {
            outWaypoints.add(point)
        }
    }

    return WaypointsOutsideGeofenceResult(
        centralWaypoint = center,
        areaRadiusKm = radius,
        waypoints = outWaypoints,
        count = outWaypoints.size
    )
}



