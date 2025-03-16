package org.example

import kotlinx.serialization.json.Json
import org.example.analyzer.findMostFrequentedArea
import org.example.analyzer.maxDistanceFromStart
import org.example.analyzer.readFromCSV
import org.example.analyzer.waypointsOutsideGeofence
import org.example.data.readCustomParameters
import org.example.data.Result
import java.io.File

const val PROPERTIES_FILE: String = "custom-parameters.yml"
const val WAYPOINTS_FILE: String = "waypoints.csv"
const val OUTPUT_FILE: String = "output.json"
const val FILE_PATH: String = "src/main/kotlin/data"


fun main(){
    val waypoints = readFromCSV(WAYPOINTS_FILE)

    val customParameters = readCustomParameters(PROPERTIES_FILE)
    val geofenceLat = customParameters.geofenceCenterLatitude
    val geofenceLon = customParameters.geofenceCenterLongitude
    val geofenceRadiusKm = customParameters.geofenceRadiusKm
    val earthRadiusKm = customParameters.earthRadiusKm
    val mostFrequentedAreaKm = customParameters.mostFrequentedAreaRadiusKm

    val maxDistance = maxDistanceFromStart(waypoints, earthRadiusKm) ?: return
    val mostFrequentedArea = findMostFrequentedArea(waypoints, mostFrequentedAreaKm?: 1.0, earthRadiusKm) ?: return
    val waypointsOutside = waypointsOutsideGeofence(waypoints, geofenceLat, geofenceLon, geofenceRadiusKm, earthRadiusKm)
    val res = Result(
        maxDistanceFromStart = maxDistance,
        mostFrequentedArea = mostFrequentedArea,
        waypointsOutsideGeofence = waypointsOutside,
    )

    if (res != null) {
        val json = Json { prettyPrint = true }.encodeToString(Result.serializer(), res)
        File("$FILE_PATH/$OUTPUT_FILE").writeText(json)
    }
}