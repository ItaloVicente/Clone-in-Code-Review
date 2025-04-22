        if (config.partitionHosts().size() != nodes.size()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Node list and configuration's partition hosts sizes : {} <> {}, rescheduling",
                        nodes.size(), config.partitionHosts().size());
            }
            return new Node[] { };
        }

