        long lookupStart = System.nanoTime();
        String lookupResult = hostname.getHostName();
        long lookupDurationMs = (System.nanoTime() - lookupStart) / 1000000L;
        if (lookupDurationMs >= DNS_RESOLUTION_THRESHOLD) {
            LOGGER.warn("DNS Reverse Lookup of " + lookupResult + " is slow, took " + lookupDurationMs + "ms");
        }

