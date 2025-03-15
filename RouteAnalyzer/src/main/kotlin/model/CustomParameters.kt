package org.example.model

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.Serializable
import java.io.FileNotFoundException

@Serializable
data class CustomParameters(
    val earthRadiusKm: Double,
    val geofenceCenterLatitude: Double,
    val geofenceCenterLongitude: Double,
    val geofenceRadiusKm: Double,
    val mostFrequentedAreaRadiusKm: Double
)

fun readCustomParameters(fileName: String): CustomParameters {
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream(fileName)
        ?: throw FileNotFoundException("File not found: $fileName")
    val yamlContent = inputStream.bufferedReader().use { it.readText() }
    return Yaml.default.decodeFromString(yamlContent)
}