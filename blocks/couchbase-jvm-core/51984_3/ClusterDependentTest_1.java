    public static boolean isMutationMetadataEnabled() throws Exception {
        ClusterConfigResponse response = cluster()
                .<ClusterConfigResponse>send(new ClusterConfigRequest(adminUser, adminPassword))
                .toBlocking()
                .single();
        return minNodeVersionFromConfig(response.config()) >= 4;
    }

