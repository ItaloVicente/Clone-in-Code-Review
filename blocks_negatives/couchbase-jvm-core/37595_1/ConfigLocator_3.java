        if (request instanceof TerseBucketConfigRequest) {
            TerseBucketConfigRequest req = (TerseBucketConfigRequest) request;
            for (Node node : nodes) {
                if (node.hostname().equals(req.hostname())) {
                    return new Node[] { node };
                }
            }
        } else if (request instanceof VerboseBucketConfigRequest) {
            VerboseBucketConfigRequest req = (VerboseBucketConfigRequest) request;
