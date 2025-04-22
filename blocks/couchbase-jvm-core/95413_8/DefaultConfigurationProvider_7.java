    private String determineNetworkResolution(final BucketConfig config) {
        for (NodeInfo info : config.nodes()) {
            if (seedHosts.contains(info.hostname())) {
                return null;
            }

            Map<String, AlternateAddress> aa = info.alternateAddresses();
            if (aa != null && !aa.isEmpty()) {
                for (Map.Entry<String, AlternateAddress> entry : aa.entrySet()) {
                    AlternateAddress alternateAddress = entry.getValue();
                    if (alternateAddress != null && seedHosts.contains(alternateAddress.hostname())) {
                        return entry.getKey();
                    }
                }
            }
        }
        return null;
    }

