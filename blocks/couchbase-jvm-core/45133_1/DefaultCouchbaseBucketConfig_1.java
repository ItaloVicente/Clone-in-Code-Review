    @Override
    public short nodeIndexForMaster(int partition) {
        return partitionInfo.partitions().get(partition).master();
    }

    @Override
    public short nodeIndexForReplica(int partition, int replica) {
        return partitionInfo.partitions().get(partition).replica(replica);
    }

    @Override
    public int numberOfPartitions() {
        return partitionInfo.partitions().size();
    }

    @Override
    public NodeInfo nodeAtIndex(int nodeIndex) {
        return nodes().get(nodeIndex);
    }

