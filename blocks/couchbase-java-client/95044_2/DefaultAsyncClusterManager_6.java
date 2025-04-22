                    })
                    .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                    .doOnNext(new Action1<ClusterConfigResponse>() {
                        @Override
                        public void call(ClusterConfigResponse response) {
                            if (!response.status().isSuccess()) {
                                if (response.config().contains("Unauthorized")) {
                                    throw new InvalidPasswordException();
                                } else {
                                    throw new CouchbaseException(response.status() + ": " + response.config());
                                }
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
            }
        });
