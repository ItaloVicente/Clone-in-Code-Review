            GetBucketConfigRequest req = (GetBucketConfigRequest) request;
            for (Node node : nodes) {
                if (node.hostname().equals(req.hostname())) {
                    return new Node[] { node };
                }
            }
            throw new IllegalStateException("Node not found for request" + request);
