
    private void retry(final ResponseEvent event) {
        CouchbaseMessage message = event.getMessage();

        if (message instanceof CouchbaseRequest) {
            cluster.send((CouchbaseRequest) message);
        } else {
            try {
                cluster.send(((CouchbaseResponse) message).toRequest());
            } catch (ToRequestNotSupportedException ex) {
                System.out.println(message + " does not support cloning");
                event.getObservable().onError(new CouchbaseException("Operation failed because it does not " +
                    "support cloning.", ex));
            }

            if (message instanceof BinaryResponse) {
                BinaryResponse response = (BinaryResponse) message;
                if (response.content() != null && response.content().readableBytes() > 0) {
                    configurationProvider.proposeBucketConfig(response.bucket(),
                        response.content().toString(CharsetUtil.UTF_8));
                }
            }
        }
    }
