        clusterManager = cluster.clusterManager(adminName, adminPassword).toBlocking().single();
        boolean exists = clusterManager.hasBucket(bucketName).toBlocking().single();
        if (!exists) {
            clusterManager.insertBucket(DefaultBucketSettings
                .builder()
                .name("default")
                .quota(256)
                .password("")
                .enableFlush(true)
                .type(BucketType.COUCHBASE)
                .build()).toBlocking().single();
        }

