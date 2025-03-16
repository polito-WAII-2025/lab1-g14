package com.routeranalyzer.analyzer

import com.routeranalyzer.data.*

interface IRouteAnalyzer {
    fun maxDistanceFromStart(startPoint: Waypoint): MaxDistanceResult
    fun mostFrequentedArea(areaRadius: Double): MostFrequentedAreaResult
    fun waypointsOutsideGeofence(geofence: Geofence): WaypointsOutsideGeofenceResult
}