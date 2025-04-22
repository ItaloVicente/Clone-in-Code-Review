        DummyService(String hostname, String bucket, String password, int port, Environment env, int numEndpoints, SelectionStrategy strategy) {
            super(hostname, bucket, password, port, env, numEndpoints, strategy, null);
        }

        @Override
        protected Endpoint newEndpoint(RingBuffer<ResponseEvent> responseBuffer) {
            return mock(Endpoint.class);
