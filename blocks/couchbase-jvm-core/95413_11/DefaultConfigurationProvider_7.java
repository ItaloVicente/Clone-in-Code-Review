    public static String determineNetworkResolution(final BucketConfig config, final NetworkResolution nr,
        final Set<NetworkAddress> seedHosts) {
        if (nr.equals(NetworkResolution.DEFAULT)) {
            return null;
        } else if (nr.equals(NetworkResolution.AUTO)) {
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
        } else {
            return nr.name();
        }
    }

