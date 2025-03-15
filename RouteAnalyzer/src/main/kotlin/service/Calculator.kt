package org.example.service

import org.example.model.Waypoint
import kotlin.math.*

fun findWaypointsOutsideGeofence(
    waypoints: List<Waypoint>,
    geofenceLat: Double,
    geofenceLon: Double,
    geofenceRadiusKm: Double,
    earthRadiusKm: Double
): List<Waypoint> {
    val center = Waypoint(0.0, geofenceLat, geofenceLon)
    return waypoints.filter { !isInRadius(center, it, geofenceRadiusKm, earthRadiusKm) }
}

fun findMostFrequentedArea(waypoints: List<Waypoint>, radiusKm: Double = 5.0, earthRadiusKm: Double): Pair<Waypoint, Int>? {
    if (waypoints.isEmpty()) return null

    var mostFrequentedWaypoint = waypoints[0]
    var maxCount = 0

    for (center in waypoints) {
        val count = waypoints.count { isInRadius(center, it, radiusKm, earthRadiusKm) }

        if (count > maxCount) {
            maxCount = count
            mostFrequentedWaypoint = center
        }
    }

    return Pair(mostFrequentedWaypoint, maxCount)
}

fun findMaximumDistancePoint(waypoints: List<Waypoint>, earthRadiusKm: Double) : Pair<Waypoint, Double>? {
    if (waypoints.isEmpty()) return null

    val startPoint = waypoints[0]
    var maxDistance = 0.0
    var farthestWaypoint = startPoint.copy()

    for (waypoint in waypoints) {
        val distance = findDistance(waypoint, startPoint, earthRadiusKm)
        if (distance > maxDistance) {
            farthestWaypoint = waypoint
            maxDistance = distance
        }
    }
    return Pair(farthestWaypoint, maxDistance)
}

fun isInRadius(waypoint1: Waypoint, waypoint2: Waypoint, radius: Double, earthRadiusKm: Double) : Boolean {
    return findDistance(waypoint1, waypoint2, earthRadiusKm) <= radius
}

fun findDistance(waypoint1: Waypoint, waypoint2: Waypoint, earthRadiusKm: Double) : Double {
    val lat1 = Math.toRadians(waypoint1.latitude)
    val lon1 = Math.toRadians(waypoint1.longitude)
    val lat2 = Math.toRadians(waypoint2.latitude)
    val lon2 = Math.toRadians(waypoint2.longitude)

    val deltaLat = lat2 - lat1
    val deltaLon = lon2 - lon1

    val a = sin(deltaLat/2).pow(2) + cos(lat1) * cos(lat2) * sin(deltaLon/2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadiusKm * c
}