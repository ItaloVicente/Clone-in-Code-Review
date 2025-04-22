    private boolean determineNetworkResolution(final BucketConfig config) {
        for (NodeInfo info : config.nodes()) {
            if (seedHosts.contains(info.hostname())) {
                return false;
            }

            Map<AlternateAddressType, AlternateAddress> aa = info.alternateAddresses();
            if (aa != null && !aa.isEmpty()) {
                AlternateAddress alternateAddress = aa.get(AlternateAddressType.EXTERNAL);
                if (alternateAddress != null && seedHosts.contains(alternateAddress.hostname())) {
                    return true;
                }
            }
        }
        return false;
    }

