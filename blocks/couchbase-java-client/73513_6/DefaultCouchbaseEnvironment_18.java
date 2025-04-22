        if (analyticsTimeout > maxRequestLifetime()) {
            LOGGER.warn("The configured analytics timeout is greater than the maximum request lifetime." +
                "This can lead to falsely cancelled requests.");
        }
        if (searchTimeout > maxRequestLifetime()) {
            LOGGER.warn("The configured search timeout is greater than the maximum request lifetime." +
                "This can lead to falsely cancelled requests.");
        }
