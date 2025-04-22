    private static boolean isValidCarrierNode(final boolean sslEnabled, final NodeInfo nodeInfo) {
        if (sslEnabled && nodeInfo.sslServices().containsKey(ServiceType.BINARY)) {
            return true;
        } else if (nodeInfo.services().containsKey(ServiceType.BINARY)) {
            return true;
        }
        return false;
    }

