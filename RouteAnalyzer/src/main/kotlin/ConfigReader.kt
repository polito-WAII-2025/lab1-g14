import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.InputStream

data class Config(
    val earthRadiusKm: Double,
    val geofenceCenterLatitude: Double,
    val geofenceCenterLongitude: Double,
    val geofenceRadiusKm: Double,
    val mostFrequentedAreaRadiusKm: Double?
)

fun loadConfig(filePath: String = "src/main/resources/custom-parameters.yml"): Config {
    val inputStream: InputStream = File(filePath).inputStream()
    val yaml = Yaml()
    val data: Map<String, Any> = yaml.load(inputStream)

    return Config(
        earthRadiusKm = (data["earthRadiusKm"] as Number).toDouble(),
        geofenceCenterLatitude = (data["geofenceCenterLatitude"] as Number).toDouble(),
        geofenceCenterLongitude = (data["geofenceCenterLongitude"] as Number).toDouble(),
        geofenceRadiusKm = (data["geofenceRadiusKm"] as Number).toDouble(),
        mostFrequentedAreaRadiusKm = (data["mostFrequentedAreaRadiusKm"] as? Number)?.toDouble()
    )
}
