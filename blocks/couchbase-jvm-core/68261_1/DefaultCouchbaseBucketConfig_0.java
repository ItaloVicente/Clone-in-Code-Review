        try {
            return partitions.get(partition).master();
        } catch (IndexOutOfBoundsException ex) {
            LOGGER.debug("Out of bounds on index for master " + partition + ".", ex);
            return PARTITION_NOT_EXISTENT;
        }
