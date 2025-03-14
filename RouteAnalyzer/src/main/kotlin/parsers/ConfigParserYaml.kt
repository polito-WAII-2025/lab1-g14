package com.routeranalyzer.parsers

import com.routeranalyzer.data.CustomParameters
import org.yaml.snakeyaml.Yaml
import java.io.InputStreamReader

class ConfigParserYaml: IParser<CustomParameters> {
    override fun parse(inputStream: InputStreamReader): CustomParameters {
        val yaml = Yaml()
        val data: Map<String, Any> = yaml.load(inputStream)
        val parameters = CustomParameters(
            data["geofenceCenterLatitude"] as Double,
            data["geofenceCenterLongitude"] as Double,
            data["earthRadiusKm"] as Double,
            data["geofenceRadiusKm"] as Double,
            data["mostFrequentedAreaRadiusKm"] as Double?
        )
        return parameters
    }
}