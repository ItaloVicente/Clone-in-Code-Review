                                settings.add(DefaultBucketSettings.builder()
                                        .name(bucket.getString("name"))
                                        .enableFlush(bucket.getObject("controllers").getString("flush") != null)
                                        .type(bucket.getString("bucketType").equals("membase")
                                                ? BucketType.COUCHBASE : BucketType.MEMCACHED)
                                        .replicas(bucket.getInt("replicaNumber"))
                                        .quota(ramQuota)
                                        .indexReplicas(bucket.getBoolean("replicaIndex"))
                                        .port(bucket.getInt("proxyPort"))
                                        .password(bucket.getString("saslPassword"))
                                        .build());
                            }
                            return Observable.from(settings);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode cluster info.", e);
