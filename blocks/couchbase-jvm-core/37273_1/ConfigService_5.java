    static class ConfigEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(String hostname, String bucket, String password, int port, Environment env,
                               RingBuffer<ResponseEvent> responseBuffer) {
            return new ConfigEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
