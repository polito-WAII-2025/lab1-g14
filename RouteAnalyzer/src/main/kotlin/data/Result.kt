package data

//@Serializable
data class MaxDistanceResult(
    val waypoint: Waypoint,
    val distanceKm: Double
)

//@Serializable
data class MostFrequentedAreaResult(
    val centralWaypoint: Waypoint,
    val areaRadiusKm: Double,
    val entriesCount: Int
)

//@Serializable
data class WaypointsOutsideGeofenceResult(
    val centralWaypoint: Waypoint,
    val areaRadiusKm: Double,
    val count: Int,
    val waypoints: List<Waypoint>
)

//@Serializable
data class OutputResult(
    val maxDistanceFromStart: MaxDistanceResult,
    val mostFrequentedArea: MostFrequentedAreaResult,
    val waypointsOutsideGeofence: WaypointsOutsideGeofenceResult
)
