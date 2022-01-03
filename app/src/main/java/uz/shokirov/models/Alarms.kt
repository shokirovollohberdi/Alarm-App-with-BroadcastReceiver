package uz.shokirov.models

import java.io.Serializable

class Alarms:Serializable {
    var name: String? = null
    var hour: Int? = null
    var min: Int? = null
    var isRun: Boolean = true
    var vibration = false

    constructor(
        name: String?,
        hour: Int?,
        min: Int?,
        isRun: Boolean,
        vibration: Boolean,
    ) {
        this.name = name
        this.hour = hour
        this.min = min
        this.isRun = isRun
        this.vibration = vibration
    }

    override fun toString(): String {
        return "Alarms(name=$name, hour=$hour, min=$min)"
    }

}