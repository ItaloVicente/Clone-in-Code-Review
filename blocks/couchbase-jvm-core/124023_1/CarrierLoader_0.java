    protected int port(final String hostname) {
        GetClusterConfigResponse response = cluster()
            .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
            .toBlocking()
            .single();
        ClusterConfig clusterConfig = response.config();

        if (!clusterConfig.bucketConfigs().isEmpty()) {
            for (BucketConfig bc : clusterConfig.bucketConfigs().values()) {
                for (NodeInfo nodeInfo : bc.nodes()) {
                    if (nodeInfo.hostname().equals(hostname)) {
                        final String alternate = nodeInfo.useAlternateNetwork();
                        if (alternate != null) {
                            AlternateAddress aa = nodeInfo.alternateAddresses().get(alternate);
                            if (!aa.services().isEmpty() && !aa.sslServices().isEmpty()) {
                                int port = env().sslEnabled()
                                    ? aa.sslServices().get(ServiceType.BINARY)
                                    : aa.services().get(ServiceType.BINARY);
                                LOGGER.trace("Picked (aa) port " + port + " for " + hostname);
                                return port;
                            }
                        }

                        int port = env().sslEnabled()
                            ? nodeInfo.sslServices().get(ServiceType.BINARY)
                            : nodeInfo.services().get(ServiceType.BINARY);
                        LOGGER.trace("Picked port " + port + " for " + hostname);
                        return port;
                    }
                }
            }
        }

        int port = env().sslEnabled() ? env().bootstrapCarrierSslPort() : env().bootstrapCarrierDirectPort();
        LOGGER.trace("Picked (default) port " + port + " for " + hostname);
        return port;
