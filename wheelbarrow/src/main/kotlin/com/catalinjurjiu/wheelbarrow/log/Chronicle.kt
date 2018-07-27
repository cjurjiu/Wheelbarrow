package com.catalinjurjiu.wheelbarrow.log

import android.util.Log
import com.catalinjurjiu.wheelbarrow.log.Chronicle.setEnabled

/**
 * Simple object used to print various logs.
 *
 * Enable or disable logging with [setEnabled]. By default logging is disabled.
 *
 * This object represents a private-API and is not intended to be used by clients of this library.
 */
internal object Chronicle {

    private var LOG_PREFIX = "WheelBarrow"
    private var enabled = false

    fun logInfo(tag: String = "", message: String) {
        if (enabled) {
            Log.i("$LOG_PREFIX$tag", message)
        }
    }

    fun logDebug(tag: String = "", message: String) {
        if (enabled) {
            Log.d("$LOG_PREFIX$tag", message)
        }
    }

    fun logWarn(tag: String = "", message: String) {
        if (enabled) {
            Log.w("$LOG_PREFIX$tag", message)
        }
    }

    fun logError(tag: String = "", message: String) {
        if (enabled) {
            Log.e("$LOG_PREFIX$tag", message)
        }
    }

    /**
     * Enables or disables logging. By default, logging is disabled.
     */
    @JvmStatic
    @JvmName("setEnabled")
    internal fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }
}