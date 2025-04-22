    @Test
    public void shouldGetBuckets() {
        clusterManager().getBuckets().toBlocking().forEach(new Action1<ClusterBucketSettings>() {
            @Override
            public void call(ClusterBucketSettings clusterBucketSettings) {
                System.err.println(clusterBucketSettings);
            }
        });
    }

    @Test
    public void shouldRemoveBucket() {
        Boolean done = clusterManager().removeBucket("foo").toBlocking().single();
        assertTrue(done);
        System.out.println(done);
    }

    @Test
    public void shouldInsertBucket() {
        ClusterBucketSettings settings = DefaultClusterBucketSettings
            .builder()
            .name("foobar")
            .quota(512000)
            .build();

        clusterManager().insertBucket(settings).toBlocking().single();
    }

    @Test
    public void shouldUpdateBucket() {

    }

