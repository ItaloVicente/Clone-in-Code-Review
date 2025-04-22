
        try {
            return partitions.get(partition).replica(replica);
        } catch (IndexOutOfBoundsException ex) {
            LOGGER.debug("Out of bounds on index for replica " + partition + ".", ex);
            return PARTITION_NOT_EXISTENT;
        }
