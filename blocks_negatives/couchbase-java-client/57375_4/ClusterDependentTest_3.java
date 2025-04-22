        env = DefaultCouchbaseEnvironment
            .builder()
            .queryEnabled(queryEnabled())
            .build();
        cluster = CouchbaseCluster.create(env, seedNode);
        clusterManager = cluster.clusterManager(adminName, adminPassword);
        boolean exists = clusterManager.hasBucket(bucketName());

        if (!exists) {
            clusterManager.insertBucket(DefaultBucketSettings
                .builder()
                .name(bucketName())
                .quota(256)
                .password(password())
                .enableFlush(true)
                .type(BucketType.COUCHBASE)
                .build());
        }

        bucket = cluster.openBucket(bucketName(), password());
        repository = bucket.repository();
        bucketManager = bucket.bucketManager();
        bucketManager.flush();
