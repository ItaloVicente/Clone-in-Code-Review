        try {
            LOGGER.debug("New Bucket {} config proposed.", bucket);
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Proposed raw config is {}", rawConfig);
            }

            JsonNode configNodes = BucketConfigParser.jackson().readTree(rawConfig);
            String bucketName = bucket == null ? configNodes.get("name").textValue() : bucket;

            JsonNode revNode = configNodes.get("rev");
            long newRev = revNode == null ? 0 : revNode.asLong();
