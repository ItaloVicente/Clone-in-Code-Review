    public void refresh(final ClusterConfig config) {
        Observable
            .from(config.bucketConfigs().values())
            .observeOn(environment.scheduler())
            .filter(new Func1<BucketConfig, Boolean>() {
                @Override
                public Boolean call(BucketConfig config) {
                    return registrations().containsKey(config.name());
                }
            })
            .filter(new Func1<BucketConfig, Boolean>() {
                @Override
                public Boolean call(BucketConfig config) {
                    String bucketName = config.name();
                    boolean allowed = allowedToPoll(bucketName);
                    if (allowed) {
                        lastPollTimestamps.put(bucketName, System.nanoTime());
                    } else {
                        LOGGER.trace("Ignoring refresh polling attempt because poll interval is too small.");
                    }
                    return allowed;
                }
            }).subscribe(new Action1<BucketConfig>() {
                @Override
                public void call(final BucketConfig config) {
                    final String bucketName = config.name();
                    List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>(config.nodes());
                    if (nodeInfos.isEmpty()) {
                        LOGGER.debug("Cannot refresh bucket, because node list contains no nodes.");
                        return;
                    }
                    shiftNodeList(nodeInfos);
                    Observable<ProposedBucketConfigContext> refreshSequence = buildRefreshFallbackSequence(nodeInfos, bucketName);
                    refreshSequence.subscribe(new Subscriber<ProposedBucketConfigContext>() {
                        @Override
                        public void onCompleted() {
                            LOGGER.debug("Completed refreshing config for bucket \"{}\"", bucketName);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LOGGER.debug("Error while refreshing bucket config, ignoring.", e);
                        }

                        @Override
                        public void onNext(ProposedBucketConfigContext ctx) {
                            if (ctx.config().startsWith("{")) {
                                provider().proposeBucketConfig(ctx);
                            }
                        }
                    });
                }
            });
    }

    private Observable<ProposedBucketConfigContext> buildRefreshFallbackSequence(final List<NodeInfo> nodeInfos,
        final String bucketName) {
        Observable<ProposedBucketConfigContext> failbackSequence = null;
        for (final NodeInfo nodeInfo : nodeInfos) {
            if (!isValidConfigNode(environment.sslEnabled(), nodeInfo)) {
                continue;
            }

            if (failbackSequence == null) {
                failbackSequence = refreshAgainstNode(bucketName, nodeInfo.hostname());
            } else {
                failbackSequence = failbackSequence.onErrorResumeNext(
                    refreshAgainstNode(bucketName, nodeInfo.hostname())
                );
            }
        }
        if (failbackSequence == null) {
            LOGGER.debug("Could not build refresh sequence, node list is empty - ignoring attempt.");
            return Observable.empty();
        }
        return failbackSequence;
    }

    private Observable<ProposedBucketConfigContext> refreshAgainstNode(final String bucketName,
        final NetworkAddress hostname) {
        final Credential credential = registrations().get(bucketName);
        if (credential == null) {
            LOGGER.debug("Ignoring refresh attempt since it seems the bucket registration is gone (closed).");
            return Observable.empty();
        }

        return Observable.defer(new Func0<Observable<BucketConfigResponse>>() {
            @Override
            public Observable<BucketConfigResponse> call() {
                return cluster()
                    .<BucketConfigResponse>send(new BucketConfigRequest(HttpLoader.TERSE_PATH, hostname, bucketName,
                        credential.username(), credential.password()))
                    .flatMap(new Func1<BucketConfigResponse, Observable<BucketConfigResponse>>() {
                        @Override
                        public Observable<BucketConfigResponse> call(BucketConfigResponse response) {
                            if (response.status().isSuccess()) {
                                LOGGER.debug("Successfully got config refresh from terse bucket remote.");
                                return Observable.just(response);
                            }

                            LOGGER.debug("Terse bucket config refresh failed, falling back to verbose.");
                            return cluster().send(
                                new BucketConfigRequest(HttpLoader.VERBOSE_PATH, hostname, bucketName,
                                    credential.username(), credential.password()));
                        }
                    });
            }
        }).map(new Func1<BucketConfigResponse, ProposedBucketConfigContext>() {
            @Override
            public ProposedBucketConfigContext call(final BucketConfigResponse response) {
                String config = response.config().replace("$HOST", hostname.address());
                return new ProposedBucketConfigContext(bucketName, config, hostname);
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable ex) {
                LOGGER.debug("Could not fetch config from bucket \"" + bucketName + "\" against \""
                    + hostname + "\".", ex);
            }
        });
    }

    private static boolean isValidConfigNode(final boolean sslEnabled, final NodeInfo nodeInfo) {
        if (sslEnabled && nodeInfo.sslServices().containsKey(ServiceType.CONFIG)) {
            return true;
        } else if (nodeInfo.services().containsKey(ServiceType.CONFIG)) {
            return true;
        }
        return false;
    }


    private <T> void shiftNodeList(List<T> nodeList) {
        int shiftBy = (int) (nodeOffset.getAndIncrement() % nodeList.size());
        for(int i = 0; i < shiftBy; i++) {
            T element = nodeList.remove(0);
            nodeList.add(element);
        }
    }

    private boolean allowedToPoll(final String bucket) {
        Long bucketLastPollTimestamp = lastPollTimestamps.get(bucket);
        return bucketLastPollTimestamp == null || ((System.nanoTime() - bucketLastPollTimestamp) >= pollFloorNs);
