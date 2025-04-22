                    }
                }
            })
            .map(new Func1<ClusterConfigResponse, ClusterInfo>() {
                @Override
                public ClusterInfo call(ClusterConfigResponse response) {
                    try {
                        return new DefaultClusterInfo(CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.stringToJsonObject(response.config()));
                    } catch (Exception e) {
                        throw new TranscodingException("Could not decode cluster info.", e);
                    }
                }
            });
