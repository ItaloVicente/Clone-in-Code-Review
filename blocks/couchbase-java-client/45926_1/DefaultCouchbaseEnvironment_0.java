
        if (queryTimeout > maxRequestLifetime()) {
            LOGGER.warn("The configured query timeout is greater than the maximum request lifetime. " +
                "This can lead to falsely cancelled requests.");
        }
        if (kvTimeout > maxRequestLifetime()) {
            LOGGER.warn("The configured key/value timeout is greater than the maximum request lifetime." +
                "This can lead to falsely cancelled requests.");
        }
        if (viewTimeout > maxRequestLifetime()) {
            LOGGER.warn("The configured view timeout is greater than the maximum request lifetime." +
                "This can lead to falsely cancelled requests.");
        }
        if (managementTimeout > maxRequestLifetime()) {
            LOGGER.warn("The configured management timeout is greater than the maximum request lifetime." +
                "This can lead to falsely cancelled requests.");
        }
