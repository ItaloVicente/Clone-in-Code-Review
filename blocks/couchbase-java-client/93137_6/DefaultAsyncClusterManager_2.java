        final AtomicInteger integer = new AtomicInteger(0);
        return Observable.just(connectionString.hosts())
                .flatMap(new Func1<List<InetSocketAddress>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<InetSocketAddress> inetSocketAddresses) {
                        int hostIndex = integer.getAndIncrement();
                        if (hostIndex >= connectionString.hosts().size()) {
                            integer.set(0);
                            return Observable.error(new CouchbaseException("Could not enable ClusterManager service to function properly."));
                        }
                        return sendAddNodeRequest(inetSocketAddresses.get(hostIndex));
