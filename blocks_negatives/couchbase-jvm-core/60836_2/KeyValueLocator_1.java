    /**
     * Special node handling for the get bucket config request.
     *
     * This is necessary to properly bootstrap the driver. if the hostnames are not equal, it is not the node where
     * the service has been enabled, so the code looks for the next one until it finds one. If none is found, the
     * operation is rescheduled.
     *
     * @param request the request to check
     * @param nodes the nodes to iterate
     * @return either the found node or an empty list indicating to retry later.
     */
    private static Node[] handleBucketConfigRequest(GetBucketConfigRequest request, List<Node> nodes) {
        return locateByHostname(request.hostname(), nodes);
    }

    private static Node[] handleStatRequest(StatRequest request, List<Node> nodes) {
        return locateByHostname(request.hostname(), nodes);
    }
