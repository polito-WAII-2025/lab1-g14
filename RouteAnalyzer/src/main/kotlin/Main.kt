import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun readWaypoints(filePath: String): List<Waypoint> {
    val waypoints = mutableListOf<Waypoint>()
    File(filePath).forEachLine { line ->
        val parts = line.split(";")
        if (parts.size == 3) {
            try {
                val timestamp = parts[0].trim().toDouble().toLong()
                val latitude = parts[1].trim().toDouble()
                val longitude = parts[2].trim().toDouble()
                waypoints.add(Waypoint(timestamp, latitude, longitude))
            } catch (e: Exception) {
                println("Error parsing line: $line - ${e.message}")
            }
        }
    }
    return waypoints
}

fun main() {
    println("Loading configuration...")
    val config = loadConfig()

    val filePath = "src/main/resources/waypoints.csv"
    val waypoints = readWaypoints(filePath)

    // the first waypoint for debugging
    println("Start Waypoint: ${waypoints.firstOrNull()}")

    val maxDistResult = maxDistanceFromStart(waypoints)

    // compute default mostFrequentedAreaRadiusKm if not provided
    val computedMostFrequentedRadius = maxDistResult?.second?.let {
        if (it < 1) 0.1 else it * 0.05
    }
    val mostFrequentedRadius = config.mostFrequentedAreaRadiusKm ?: computedMostFrequentedRadius ?: 0.5

    val mostFrequentedResult = mostFrequentedArea(waypoints, mostFrequentedRadius)

    val waypointsOutside = waypointsOutsideGeofence(
        waypoints,
        config.geofenceCenterLatitude,
        config.geofenceCenterLongitude,
        config.geofenceRadiusKm
    )

    val output = mapOf(
        "maxDistanceFromStart" to mapOf(
            "waypoint" to maxDistResult?.first,
            "distanceKm" to maxDistResult?.second
        ),
        "mostFrequentedArea" to mapOf(
            "centralWaypoint" to mostFrequentedResult?.first,
            "areaRadiusKm" to mostFrequentedRadius,
            "entriesCount" to mostFrequentedResult?.second
        ),
        "waypointsOutsideGeofence" to mapOf(
            "centralWaypoint" to mapOf(
                "latitude" to config.geofenceCenterLatitude,
                "longitude" to config.geofenceCenterLongitude
            ),
            "areaRadiusKm" to config.geofenceRadiusKm,
            "count" to waypointsOutside.size,
            "waypoints" to waypointsOutside
        )
    )

    val objectMapper = jacksonObjectMapper()
    File("src/main/resources/output.json").writeText(objectMapper.writeValueAsString(output))

    println("Processing completed. Output written to src/main/resources/output.json")
}
