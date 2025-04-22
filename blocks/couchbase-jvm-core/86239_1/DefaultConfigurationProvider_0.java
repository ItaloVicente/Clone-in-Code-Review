            JsonNode configNodes = BucketConfigParser.jackson().readTree(rawConfig);
            String bucketName = bucket == null ? configNodes.get("name").textValue() : bucket;
            long newRev = configNodes.get("rev").asLong();

            BucketConfig oldConfig = currentConfig.bucketConfig(bucketName);
            if (newRev > 0 && oldConfig != null && newRev <= oldConfig.rev()) {
                LOGGER.trace("Not applying new configuration, older or same rev ID.");
                return;
            }

            BucketConfig config = BucketConfigParser.parse(rawConfig, environment);
            upsertBucketConfig(config);
        } catch (Exception ex) {
            LOGGER.warn("Could not read proposed configuration, ignoring.", ex);
        }
