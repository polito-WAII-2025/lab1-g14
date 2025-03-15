package com.routeranalyzer

import org.gavaghan.geodesy.Ellipsoid

object Config {
    val REF_ELLIPSOID: Ellipsoid = Ellipsoid.WGS84
    const val PROPERTIES_FILE: String = "custom-properties.yml"
    const val WAYPOINTS_FILE: String = "waypoints.csv"
    const val OUTPUT_FILE: String = "output.json"
    const val MOUNT_FOLDER: String = "evaluation"
}