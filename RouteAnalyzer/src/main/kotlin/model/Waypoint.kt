package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Waypoint(val timestamp : Double, val longitude : Double, val latitude : Double)