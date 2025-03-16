package analyzer

import data.Waypoint
import kotlin.math.*

fun maxDistanceFromStart(waypoints: List<Waypoint>, earthRadiusKm: Double) : Pair<Waypoint, Double>? {
    if (waypoints.isEmpty()) return null

    val startPoint = waypoints[0]
    var maxDistance = 0.0
    var farthestWaypoint = startPoint.copy()

    for (waypoint in waypoints) {
        val distance = haversineDistance(waypoint, startPoint, earthRadiusKm)
        if (distance > maxDistance) {
            farthestWaypoint = waypoint
            maxDistance = distance
        }
    }
    return Pair(farthestWaypoint, maxDistance)
}

fun haversineDistance(waypoint1: Waypoint, waypoint2: Waypoint, earthRadiusKm: Double) : Double  {
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