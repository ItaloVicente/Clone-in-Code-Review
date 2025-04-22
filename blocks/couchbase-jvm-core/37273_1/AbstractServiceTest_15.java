
        public static class DummyEndpointFactory implements EndpointFactory {

            Iterator<Endpoint> endpoints;
            public DummyEndpointFactory(Iterator<Endpoint> endpoints) {
                this.endpoints = endpoints;
            }

            @Override
            public Endpoint create(String hostname, String bucket, String password, int port, Environment env,
                RingBuffer<ResponseEvent> responseBuffer) {
                return endpoints.next();
            }

        }

