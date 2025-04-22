    static class StreamEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(String hostname, String bucket, String password, int port, Environment env,
            RingBuffer<ResponseEvent> responseBuffer) {
            throw new UnsupportedOperationException("Implement streaming endpoint factory.");
        }
