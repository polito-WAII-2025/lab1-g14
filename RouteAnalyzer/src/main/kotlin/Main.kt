package org.example


import org.example.model.readCustomParameters
import org.example.service.*

fun main() {
    val filePath = "waypoints.csv"
    val waypoints = readWaypointsFromCSV(filePath)
    if (waypoints.isEmpty()) {
        println("No waypoints found")
        return
    }

    val customParameters = readCustomParameters("custom-parameters.yml")
    val geofenceLat = customParameters.geofenceCenterLatitude
    val geofenceLon = customParameters.geofenceCenterLongitude
    val geofenceRadiusKm = customParameters.geofenceRadiusKm
    val earthRadiusKm = customParameters.earthRadiusKm
    val mostFrequentedAreaKm = customParameters.mostFrequentedAreaRadiusKm

    val (farthestWaypoint, maximumDistance) = findMaximumDistancePoint(waypoints, earthRadiusKm) ?: return
    val (mostFrequentedWaypoint, mostFrequentedCount) = findMostFrequentedArea(waypoints, mostFrequentedAreaKm, earthRadiusKm) ?: return

    val waypointsOutside = findWaypointsOutsideGeofence(waypoints, geofenceLat, geofenceLon, geofenceRadiusKm, earthRadiusKm)
    saveResultsToJson(farthestWaypoint, maximumDistance, mostFrequentedWaypoint, mostFrequentedCount,
        waypointsOutside, geofenceLat, geofenceLon, geofenceRadiusKm)
}