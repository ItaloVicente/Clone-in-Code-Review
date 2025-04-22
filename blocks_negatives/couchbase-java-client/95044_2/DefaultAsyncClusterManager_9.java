                    }
                }
            })
            .flatMap(new Func1<BucketsConfigResponse, Observable<BucketSettings>>() {
                @Override
                public Observable<BucketSettings> call(BucketsConfigResponse response) {
                    try {
                        JsonArray decoded = CouchbaseAsyncBucket.JSON_ARRAY_TRANSCODER.stringToJsonArray(response.config());
                        List<BucketSettings> settings = new ArrayList<BucketSettings>();
                        for (Object item : decoded) {
                            JsonObject bucket = (JsonObject) item;
                            JsonObject controllers = bucket.getObject("controllers");
                            boolean enableFlush = controllers != null && controllers.getString("flush") != null;
                            Boolean replicaIndex = bucket.getBoolean("replicaIndex");
                            boolean indexReplicas = replicaIndex != null ? replicaIndex : false;
                            int ramQuota = 0;
                            if (bucket.getObject("quota").get("ram") instanceof Long) {
                                ramQuota = (int) (bucket.getObject("quota").getLong("ram") / 1024 / 1024);
                            } else {
                                ramQuota = bucket.getObject("quota").getInt("ram") / 1024 / 1024;
