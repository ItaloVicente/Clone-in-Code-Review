    public void send(final CouchbaseRequest request) {
        if (request instanceof SignalFlush) {
            for (int i = 0; i < endpoints.length; i++) {
                endpoints[i].send(request);
            }
            return;
        }
        strategy.select(request, endpoints).send(request);
