
    abstract class AbstractEndpointFactory implements EndpointFactory {

        public Endpoint create(String hostname, String bucket, String password, int port, CoreEnvironment env,
                        RingBuffer<ResponseEvent> responseBuffer) {
            return create(hostname, bucket, bucket, password, port, env, responseBuffer);

        }

        public abstract Endpoint create(String hostname, String bucket, String username, String password, int port, CoreEnvironment env,
                        RingBuffer<ResponseEvent> responseBuffer);


    }
