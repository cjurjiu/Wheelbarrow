package com.catalinjurjiu.wheelbarrow;

import com.catalinjurjiu.wheelbarrow.log.Chronicle;

/**
 * Contains various configuration hooks for Wheelbarrow.
 */
public class WheelBarrowConfig {

    /**
     * Enables or disables logging by Wheelbarrow.
     * <p>
     * Logging is enabled by default.
     *
     * @param debugLogsEnabled
     */
    public static void setDebugLogsEnabled(boolean debugLogsEnabled) {
        Chronicle.setEnabled(debugLogsEnabled);
    }
}