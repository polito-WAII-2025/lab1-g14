package com.routeranalyzer.parsers

import com.routeranalyzer.data.CustomParameters
import org.yaml.snakeyaml.Yaml
import java.io.InputStreamReader

class ConfigParserYaml: IParser<CustomParameters> {
    override fun parse(inputStream: InputStreamReader): CustomParameters {
        val yaml = Yaml()
        val data: Map<String, Any> = yaml.load(inputStream)
        val parameters = CustomParameters(
            geofenceCenterLatitude = data["geofenceCenterLatitude"] as Double,
            geofenceCenterLongitude = data["geofenceCenterLongitude"] as Double,
            earthRadiusKm = data["earthRadiusKm"] as Double,
            geofenceRadiusKm = data["geofenceRadiusKm"] as Double,
            mostFrequentedAreaRadiusKm = data["mostFrequentedAreaRadiusKm"] as Double?
        )
        return parameters
    }
}