            .addLast("BinaryMemcacheClientCodec", new BinaryMemcacheClientCodec())
            .addLast("BinaryMemcacheObjectAggregator", new BinaryMemcacheObjectAggregator(Integer.MAX_VALUE))
            .addLast("KeyValueFeatureHandler", new KeyValueFeatureHandler(environment(), bucket()))
            .addLast("KeyValueAuthHandler", new KeyValueAuthHandler(username(), password()))
            .addLast("KeyValueSelectBucketHandler", new KeyValueSelectBucketHandler(bucket()))
            .addLast("KeyValueHandler", new KeyValueHandler(this, responseBuffer(), false, true));
