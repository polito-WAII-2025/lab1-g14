import analyzer.readFromCSV

fun main(){
    val filePath = "waypoints.csv"
    val waypoints = readFromCSV(filePath)
    waypoints.forEach { println(it) }
}