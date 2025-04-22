
        Set<InetAddress> configNodes = new HashSet<InetAddress>();
        nodeLock.readLock().lock();
        try {
            for (Map.Entry<String, BucketConfig> bucket : config.bucketConfigs().entrySet()) {
                BucketConfig bucketConfig = bucket.getValue();
                for (final NodeInfo node : bucketConfig.nodes()) {
                    configNodes.add(node.hostname());
                }
                reconfigureBucket(bucketConfig);
            }
        } finally {
            nodeLock.readLock().unlock();
        }

        nodeLock.writeLock().lock();
        try {
            for (Node node : nodes) {
                if (!configNodes.contains(node.hostname())) {
                    removeNode(node);
                    node.disconnect().subscribe();
                }
            }
        } finally {
            nodeLock.writeLock().unlock();
