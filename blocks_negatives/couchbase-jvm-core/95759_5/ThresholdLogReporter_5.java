
    /**
     * This method is intended to be overridden in test implementations
     * to assert against the output.
     */
    void logZombies(final List<Map<String, Object>> toLog) {
        try {
            String result = pretty
                ? prettyWriter().writeValueAsString(toLog)
                : writer().writeValueAsString(toLog);
            LOGGER.warn("Zombie responses observed: {}", result);
        } catch (Exception ex) {
            LOGGER.warn("Could not write zombie log.", ex);
        }
    }

