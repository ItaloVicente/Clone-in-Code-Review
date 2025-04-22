        int size = clusterManager.getBucket(UPDATE_BUCKET).quota();

        clusterManager.removeBucket(UPDATE_BUCKET);
        assertEquals(256, size);


