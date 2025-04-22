                    if (AbstractEndpoint.this instanceof KeyValueEndpoint && ctx != null) {
                        BucketConfig config = ctx.configurationProvider().config().bucketConfig(bucket);
                        if (config instanceof CouchbaseBucketConfig) {
                            if(!((CouchbaseBucketConfig) config)
                                .hasPrimaryPartitionsOnNode(NetworkAddress.create(hostname))) {
                                LOGGER.debug("Error during reconnect: {}", e.toString());
                                return;
                            }
                        }
                    }

