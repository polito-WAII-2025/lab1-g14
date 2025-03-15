package com.routeranalyzer.parsers

import com.routeranalyzer.data.Waypoint
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.InputStreamReader

class WaypointsParserCSV : IParser<List<Waypoint>> {
    override fun parse(inputStream: InputStreamReader): List<Waypoint> {
        val csvFormat = CSVFormat.DEFAULT.builder().setDelimiter(";").build()
        val parser = CSVParser(inputStream, csvFormat)
        val wayPoints = mutableListOf<Waypoint>()

        for (record in parser.records) {
            wayPoints.add(
                Waypoint(
                    record.get(1).toDouble(),
                    record.get(2).toDouble(),
                    record.get(0).toDouble()
                )
            )
        }
        return wayPoints
    }
}