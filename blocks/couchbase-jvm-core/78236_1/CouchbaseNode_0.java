        if (Boolean.parseBoolean(System.getProperty(REVERSE_DNS_PROPERTY, "true"))) {
            long lookupStart = System.nanoTime();
            String lookupResult = hostname.nameAndAddress();
            long lookupDurationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - lookupStart);
            if (lookupDurationMs >= DNS_RESOLUTION_THRESHOLD) {
                LOGGER.warn("DNS Reverse Lookup of " + lookupResult + " is slow, took " + lookupDurationMs + "ms");
            }
