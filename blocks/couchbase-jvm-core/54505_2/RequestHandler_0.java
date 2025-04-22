            LOGGER.debug("No open bucket found in config, disconnecting all nodes.");
            Set<Node> snapshotNodes;
            synchronized (nodes) {
                snapshotNodes = new HashSet<Node>(nodes);
            }
            if (snapshotNodes.isEmpty()) {
