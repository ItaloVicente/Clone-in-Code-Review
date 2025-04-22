                                    settings.add(DefaultBucketSettings.builder()
                                        .name(bucket.getString("name"))
                                        .enableFlush(enableFlush)
                                        .type(bucketType)
                                        .replicas(bucket.getInt("replicaNumber"))
                                        .quota(ramQuota)
                                        .indexReplicas(indexReplicas)
                                        .port(bucket.getInt("proxyPort"))
                                        .password(bucket.getString("saslPassword"))
                                        .build(bucket));
                                }
                                return Observable.from(settings);
                            } catch (Exception e) {
                                throw new TranscodingException("Could not decode cluster info.", e);
                            }
