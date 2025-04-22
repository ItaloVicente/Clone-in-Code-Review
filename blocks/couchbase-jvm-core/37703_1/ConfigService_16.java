    @Override
    public void send(final CouchbaseRequest request) {
        if (request instanceof BucketStreamingRequest) {
            final Endpoint endpoint = factory.create(hostname, bucket, password, port, env, responseBuffer);
            endpointStates.add(endpoint.states());
            endpoint
                .connect()
                .subscribe(new Subscriber<LifecycleState>() {
                    @Override
                    public void onCompleted() {
                        pinnedEndpoints.add(endpoint);
                        endpoint.send(request);
                        endpoint.send(SignalFlush.INSTANCE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        request.observable().onError(e);
                    }

                    @Override
                    public void onNext(LifecycleState state) {

                    }
                });
        } else {
            super.send(request);
        }
    }

