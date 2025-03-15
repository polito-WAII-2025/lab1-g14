fun maxDistanceFromStart(waypoints: List<Waypoint>): Pair<Waypoint, Double>? {
    if (waypoints.isEmpty()) return null

    val start = waypoints.first()
    var maxDistance = 0.0
    var farthestWaypoint = start

    for (wp in waypoints) {
        val distance = haversineDistance(start.latitude, start.longitude, wp.latitude, wp.longitude)
        if (distance > maxDistance) {
            maxDistance = distance
            farthestWaypoint = wp
        }
    }

    return farthestWaypoint to maxDistance
}

fun mostFrequentedArea(waypoints: List<Waypoint>, radiusKm: Double = 0.5): Pair<Waypoint, Int>? {
    if (waypoints.isEmpty()) return null

    var bestWaypoint = waypoints[0]
    var maxCount = 0

    for (wp in waypoints) {
        val count = waypoints.count {
            haversineDistance(wp.latitude, wp.longitude, it.latitude, it.longitude) <= radiusKm
        }
        if (count > maxCount) {
            maxCount = count
            bestWaypoint = wp
        }
    }

    return bestWaypoint to maxCount
}

fun waypointsOutsideGeofence(
    waypoints: List<Waypoint>,
    centerLat: Double,
    centerLon: Double,
    radiusKm: Double
): List<Waypoint> {
    return waypoints.filter { wp ->
        haversineDistance(centerLat, centerLon, wp.latitude, wp.longitude) > radiusKm
    }
}
