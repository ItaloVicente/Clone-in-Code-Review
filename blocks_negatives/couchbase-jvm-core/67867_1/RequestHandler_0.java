                    Set<InetAddress> configNodes = new HashSet<InetAddress>();
                    for (Map.Entry<String, BucketConfig> bucket : config.bucketConfigs().entrySet()) {
                        for (final NodeInfo node : bucket.getValue().nodes()) {
                            configNodes.add(node.hostname());
                        }
                    }
