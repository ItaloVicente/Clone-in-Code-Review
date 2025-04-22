    short nodeIndexForMaster(int partition);

    short nodeIndexForReplica(int partition, int replica);

    int numberOfPartitions();

    NodeInfo nodeAtIndex(int nodeIndex);
