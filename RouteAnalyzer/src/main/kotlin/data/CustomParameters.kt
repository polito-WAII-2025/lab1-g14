package data

import java.io.File

@Serializable
data class CustomParameters(
    val earthRadiusKm: Double,
    val geofenceCenterLatitude: Double,
    val geofenceCenterLongitude: Double,
    val geofenceRadiusKm: Double,
    val mostFrequentedAreaRadiusKm: Double?
    )

fun readCustomParameters(fileName: String): CustomParameters {
    val file = File("src/main/resources/$fileName")
    if (!file.exists()) throw IllegalArgumentException("File not found: $fileName")

    val yamlContent = file.readText()
    return Yaml.default.decodeFromString(CustomParameters.serializer(), yamlContent)
}
