                            if (provider() != null) {
                                ClusterConfig config = provider().config();
                                if (config != null && !config.bucketConfigs().isEmpty()) {
                                    refresh(config);
                                } else {
                                    LOGGER.debug("No bucket open to refresh, ignoring outdated signal.");
                                }
                            } else {
                                LOGGER.debug("Provider not yet wired up, ignoring outdated signal.");
                            }
