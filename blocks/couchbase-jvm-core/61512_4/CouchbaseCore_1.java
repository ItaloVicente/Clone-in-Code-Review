        } else if (request instanceof OpenConnectionRequest) {
            final CouchbaseCore core = this;
            configProvider
                    .openBucket(request.bucket(), request.password())
                    .flatMap(new Func1<ClusterConfig, Observable<ClusterConfig>>() {
                        @Override
                        public Observable<ClusterConfig> call(ClusterConfig clusterConfig) {
                            return requestHandler.reconfigure(clusterConfig);
                        }
                    })
                    .map(new Func1<ClusterConfig, OpenConnectionResponse>() {
                        @Override
                        public OpenConnectionResponse call(final ClusterConfig clusterConfig) {
                            if (clusterConfig.hasBucket(request.bucket())) {
                                return new OpenConnectionResponse(
                                        new DCPConnection(environment, core, request.bucket(), request.password()),
                                        ResponseStatus.SUCCESS);
                            }
                            throw new CouchbaseException("Could not open bucket.");
                        }
                    })
                    .subscribe(request.observable());
