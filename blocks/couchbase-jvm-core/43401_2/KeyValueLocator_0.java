        if (config.partitionHosts().size() != nodes.size()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Node list and configuration's partition hosts have different sizes : "
                        + nodes.size() + " vs " + config.partitionHosts().size());
            }
            return new Node[] { };
        }

