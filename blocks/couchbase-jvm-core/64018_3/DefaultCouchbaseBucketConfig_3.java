    public short nodeIndexForMaster(int partition, boolean useFastForward) {
        if (useFastForward && !hasFastForwardMap()) {
            throw new IllegalStateException("Could not get index from FF-Map, none found in this config.");
        }

        List<Partition> partitions = useFastForward ? partitionInfo.forwardPartitions() : partitionInfo.partitions();
        return partitions.get(partition).master();
