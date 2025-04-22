        NodeInfo nodeInfo = config.nodeAtIndex(nodeId);

        if (config.nodes().size() != nodes.size()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Node list and configuration's partition hosts sizes : {} <> {}, rescheduling",
                        nodes.size(), config.nodes().size());
            }
            return new Node[] { };
        }
