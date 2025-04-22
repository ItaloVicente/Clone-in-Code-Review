
                            BucketType bucketType;
                            String rawType = bucket.getString("bucketType");
                            if ("membase".equalsIgnoreCase(rawType)) {
                                bucketType = BucketType.COUCHBASE;
                            } else if ("ephemeral".equalsIgnoreCase(rawType)) {
                                bucketType = BucketType.EPHEMERAL;
                            } else {
                                bucketType = BucketType.MEMCACHED;
                            }
