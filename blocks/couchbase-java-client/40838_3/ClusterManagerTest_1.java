    @Test
    public void shouldInsertBucket() {
        ClusterBucketSettings settings = DefaultClusterBucketSettings
            .builder()
            .name("insertBucket")
            .password("password")
            .quota(128)
            .build();

        clusterManager.insertBucket(settings).toBlocking().single();
    }

    @Test
    public void shouldGetBuckets() {
        Observable
            .just("bucket1", "bucket2")
            .map(new Func1<String, ClusterBucketSettings>() {
                @Override
                public ClusterBucketSettings call(final String name) {
                    return DefaultClusterBucketSettings
                        .builder()
                        .name(name)
                        .password("password")
                        .quota(128)
                        .build();
                }
            })
            .flatMap(new Func1<ClusterBucketSettings, Observable<?>>() {
                @Override
                public Observable<?> call(ClusterBucketSettings settings) {
                    return clusterManager.insertBucket(settings);
                }
            }).toBlocking().last();


        List<ClusterBucketSettings> settings = clusterManager.getBuckets().toList().toBlocking().single();
        assertEquals(2, settings.size());
        for (ClusterBucketSettings bucket : settings) {
            assertTrue(bucket.name().equals("bucket1") || bucket.name().equals("bucket2"));
        }
    }

    @Test
    public void shouldRemoveBucket() {
        ClusterBucketSettings settings = DefaultClusterBucketSettings
            .builder()
            .name("removeBucket")
            .password("password")
            .quota(128)
            .build();

        clusterManager.insertBucket(settings).toBlocking().single();

        assertEquals(1, clusterManager.getBuckets().toList().toBlocking().single().size());
        assertTrue(clusterManager.removeBucket("removeBucket").toBlocking().single());
        assertEquals(0, clusterManager.getBuckets().toList().toBlocking().single().size());
    }

    @Test
    @Ignore
    public void shouldUpdateBucket() {
        ClusterBucketSettings settings = DefaultClusterBucketSettings
            .builder()
            .name("updateBucket")
            .password("password")
            .quota(128)
            .build();

        clusterManager.insertBucket(settings).toBlocking().single();

        settings = DefaultClusterBucketSettings
            .builder()
            .name("updateBucket")
            .password("password")
            .quota(256)
            .build();

        clusterManager.updateBucket(settings).toBlocking().single();
        assertEquals(256, clusterManager.getBucket("updateBucket").toBlocking().single().quota());
    }

