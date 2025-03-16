package analyzer

import data.Waypoint
import java.io.File

fun readFromCSV(fileName: String) : List<Waypoint>{
    val waypoints = mutableListOf<Waypoint>()

    val file = File("src/main/resources/$fileName")

    if (!file.exists()) {
        println("File not found: $fileName")
        return emptyList()
    }

    file.bufferedReader().useLines { lines ->
        lines.forEach { line ->
                val tokens = line.split(";")
                if (tokens.size == 3) {
                    val timestamp = tokens[0].toDoubleOrNull() ?: 0.0
                    val latitude = tokens[1].toDoubleOrNull() ?: 0.0
                    val longitude = tokens[2].toDoubleOrNull() ?: 0.0

                    waypoints.add(Waypoint(timestamp, latitude, longitude))
                }
            }
    }

    return waypoints
}

fun csvToJSON( farthestWaypoint: Waypoint, maxDistance: Double, mostFrequentedWaypoint: Waypoint, mostFrequentedCount: Int,
               waypointsOutside: List<Waypoint>, geofenceLat: Double, geofenceLon: Double, geofenceRadiusKm: Double){
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