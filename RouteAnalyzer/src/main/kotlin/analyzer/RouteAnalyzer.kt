package com.routeranalyzer.analyzer

import com.routeranalyzer.data.*

class RouteAnalyzer(
    private val waypoints: List<Waypoint>
): IRouteAnalyzer {
    override fun maxDistanceFromStart(startPoint: Waypoint): MaxDistanceResult {
        var maxDistance = 0.0
        var maxWaypoint = startPoint
        for (point in waypoints) {
            val newMax = maxOf(startPoint.calculateDistance(point), maxDistance)
            if (newMax > maxDistance) {
                maxWaypoint = point
                maxDistance = newMax
            }
        }
        return MaxDistanceResult(
            waypoint = maxWaypoint,
            distanceKm = maxDistance
        )
    }

    override fun mostFrequentedArea(areaRadius: Double): MostFrequentedAreaResult {
        var mostFrequentAreaEntries = 0
        var mostFrequentArea: Waypoint = waypoints.first()

        for (areaPoint in waypoints){
            val areaGeofence = Geofence(areaPoint, areaRadius)
            var entries = 0
            waypoints.forEach { if(areaGeofence.isInRadius(it)) entries++ }
            if (mostFrequentAreaEntries <= entries){
                mostFrequentAreaEntries = entries
                mostFrequentArea = areaPoint
            }
        }

        return MostFrequentedAreaResult(
            centralWaypoint = mostFrequentArea,
            areaRadiusKm = areaRadius,
            entriesCount = mostFrequentAreaEntries
        )
    }

    override fun waypointsOutsideGeofence(geofence: Geofence): WaypointsOutsideGeofenceResult {
        val outWayPoints = mutableListOf<Waypoint>()
        for (point in waypoints) {
            if (geofence.isInRadius(point)) continue
            outWayPoints.add(point)
        }
        return  WaypointsOutsideGeofenceResult(
            centralWaypoint = geofence.point,
            areaRadiusKm = geofence.geofenceRadiusKm,
            waypoints = outWayPoints,
            count = outWayPoints.size
        )
    }

}