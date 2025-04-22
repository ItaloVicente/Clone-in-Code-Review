
    interface EndpointFactory {

        Endpoint create(String hostname, String bucket, String password, int port, Environment env,
            RingBuffer<ResponseEvent> responseBuffer);

    }
