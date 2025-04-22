        final BucketStreamAggregatorState state = new BucketStreamAggregatorState(DEFAULT_CONNECTION_NAME);
        int numPartitions = partitionSize().toBlocking().first();
        for (short partition = 0; partition < numPartitions; partition++) {
            state.put(new BucketStreamState(partition, 0, 0, 0xffffffff, 0, 0xffffffff));
        }
        return feed(state);
