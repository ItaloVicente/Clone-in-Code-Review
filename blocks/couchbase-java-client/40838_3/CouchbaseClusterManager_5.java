                public Observable<ClusterBucketSettings> call(BucketsConfigResponse response) {
                    try {
                        JsonArray decoded = CouchbaseBucket.JSON_TRANSCODER.stringTojsonArray(response.config());
                        List<ClusterBucketSettings> settings = new ArrayList<ClusterBucketSettings>();
                        for (Object item : decoded) {
                            JsonObject bucket = (JsonObject) item;
                            settings.add(DefaultClusterBucketSettings.builder()
                                .name(bucket.getString("name"))
                                .enableFlush(bucket.getObject("controllers").getString("flush") != null)
                                .type(bucket.getString("bucketType").equals("membase")
                                    ? BucketType.COUCHBASE : BucketType.MEMCACHED)
                                .replicas(bucket.getInt("replicaNumber"))
                                .quota(bucket.getObject("quota").getInt("ram"))
                                .indexReplicas(bucket.getBoolean("replicaIndex"))
                                .port(bucket.getInt("proxyPort"))
                                .password(bucket.getString("saslPassword"))
                                .build());
                        }
                        return Observable.from(settings);
                    } catch (Exception e) {
                        throw new CouchbaseException("Could not decode cluster info.", e);
                    }
