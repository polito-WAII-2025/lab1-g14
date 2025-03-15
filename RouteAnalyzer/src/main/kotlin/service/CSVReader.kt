package org.example.service

import org.example.model.Waypoint
import java.io.File
import java.io.FileNotFoundException

fun readWaypointsFromCSV(fileName: String): List<Waypoint> {
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream(fileName)
        ?: throw FileNotFoundException("File not found: $fileName")
    val waypoints = mutableListOf<Waypoint>()
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(";")
            if (parts.size == 3) {
                try {
                    val timestamp = parts[0].toDouble()
                    val longitude = parts[1].toDouble()
                    val latitude = parts[2].toDouble()
                    waypoints.add(Waypoint(timestamp.toDouble(), longitude, latitude))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
    return waypoints
}