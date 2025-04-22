    private String mapExternalToLogicalHostname(final String seedHostname, final int port) {
        GetClusterConfigResponse response = cluster()
            .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
            .toBlocking()
            .single();
        ClusterConfig clusterConfig = response.config();

        if (!clusterConfig.bucketConfigs().isEmpty()) {
            for (BucketConfig bc : clusterConfig.bucketConfigs().values()) {
                for (NodeInfo nodeInfo : bc.nodes()) {
                    String alternate = bc.useAlternateNetwork();
                    if (alternate != null) {
                        AlternateAddress aa = nodeInfo.alternateAddresses().get(alternate);
                        if (aa.hostname().equals(seedHostname)) {
                            int aaPort = env().sslEnabled()
                                ? aa.sslServices().get(serviceType)
                                : aa.services().get(serviceType);
                            if (aaPort == port) {
                                return nodeInfo.hostname();
                            }
                        }
                    }
                }
            }
        }

        return seedHostname;
    }

