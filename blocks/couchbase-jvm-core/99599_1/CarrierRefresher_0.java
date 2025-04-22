                                ClusterConfig config = provider().config();
                                if (config != null && !config.bucketConfigs().isEmpty()) {
                                    refresh(config);
                                } else {
                                    LOGGER.debug("No bucket open to refresh, ignoring outdated signal.");
                                }
