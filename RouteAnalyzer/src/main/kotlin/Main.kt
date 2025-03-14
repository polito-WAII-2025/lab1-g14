package com.routeranalyzer

import com.routeranalyzer.analyzer.RouteAnalyzer
import com.routeranalyzer.data.Geofence
import com.routeranalyzer.data.OutputResult
import com.routeranalyzer.data.Waypoint
import com.routeranalyzer.parsers.ConfigParserYaml
import com.routeranalyzer.parsers.WaypointsParserCSV
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileReader

@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    prettyPrint = true
    prettyPrintIndent = "\t"
}

fun main() {

    val csvFile = FileReader("waypoints.csv")
    val configFile = FileReader("custom-parameters.yml")

    val waypointsParser = WaypointsParserCSV()
    val configParser = ConfigParserYaml()

    try {
        val config = configParser.parse(configFile)
        val waypoints = waypointsParser.parse(csvFile)
        val analyzer = RouteAnalyzer(waypoints)

        val maxDistanceResult = analyzer.maxDistanceFromStart(waypoints.first())
        val mostFrequentedAreaResult = analyzer.mostFrequentedArea(config.mostFrequentedAreaRadiusKm.let {
            maxOf(
                0.1,
                maxDistanceResult.distanceKm * 0.1
            )
        })
        val waypointsOutsideGeofenceResult = analyzer.waypointsOutsideGeofence(
            Geofence(
                point = Waypoint(
                    latitude = config.geofenceCenterLatitude,
                    longitude = config.geofenceCenterLongitude
                ),
                geofenceRadius = config.geofenceRadiusKm
            )
        )
        val out = OutputResult(
            maxDistanceFromStart = maxDistanceResult,
            mostFrequentedArea = mostFrequentedAreaResult,
            waypointsOutsideGeofence = waypointsOutsideGeofenceResult,
        )

        File("output.json").writeText(json.encodeToString (out))

    } finally {
        csvFile.close()
        configFile.close()
    }

}