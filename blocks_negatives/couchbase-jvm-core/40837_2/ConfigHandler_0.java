        HttpMethod httpMethod;

        if (msg instanceof BucketConfigRequest) {
            httpMethod = HttpMethod.GET;
        } else if (msg instanceof ClusterConfigRequest) {
            httpMethod = HttpMethod.GET;
        } else if (msg instanceof BucketStreamingRequest) {
            httpMethod = HttpMethod.GET;
        } else if(msg instanceof FlushRequest) {
