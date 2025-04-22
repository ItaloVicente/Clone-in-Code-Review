    /**
     * Checks based on the cluster node versions if DCP is available.
     *
     * @return true if all nodes in the cluster are version 3 or later.
     */
    public static boolean isDCPEnabled() throws Exception {
        return minNodeVersion()[0] >= 3;
    }

