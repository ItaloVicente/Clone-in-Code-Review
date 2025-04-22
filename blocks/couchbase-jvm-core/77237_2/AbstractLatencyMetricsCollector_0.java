        LatencyStats removed = latencyMetrics.remove(identifier);
        if (removed != null) {
            try {
                removed.stop();
            } catch (Exception ex) {
                LOGGER.warn("Caught exception while removing LatencyStats, moving on.", ex);
            }
        }
