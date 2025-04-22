        int nodeId = calculateNodeId(partitionId, request, config);
        if (nodeId < 0) {
            return errorObservables(nodeId, request, config.name());
        }

        NodeInfo nodeInfo = config.nodeAtIndex(nodeId);
        if (config.nodes().size() != nodes.size()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Node list and configuration's partition hosts sizes : {} <> {}, rescheduling",
                        nodes.size(), config.nodes().size());
            }
            return EMPTY_NODES;
        }

        for (Node node : nodes) {
            if (node.hostname().equals(nodeInfo.hostname())) {
                return new Node[] { node };
            }
        }

        throw new IllegalStateException("Node not found for request" + request);
    }

    private static int calculateNodeId(int partitionId, BinaryRequest request, CouchbaseBucketConfig config) {
