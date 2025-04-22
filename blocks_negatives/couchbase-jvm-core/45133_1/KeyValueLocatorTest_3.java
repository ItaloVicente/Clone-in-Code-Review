        when(bucketMock.partitions()).thenReturn(Arrays.asList(
            new DefaultPartition((short) 0, new short[] {1}),
            new DefaultPartition((short) 0, new short[] {1}),
            new DefaultPartition((short) 1, new short[] {0}),
            (Partition) new DefaultPartition((short) 1, new short[] {0})
        ));
        when(bucketMock.partitionHosts()).thenReturn(Arrays.asList(
            (NodeInfo) new DefaultNodeInfo("foo", "192.168.56.101:11210", Collections.EMPTY_MAP),
            new DefaultNodeInfo("foo", "192.168.56.102:11210", Collections.EMPTY_MAP)
        ));
