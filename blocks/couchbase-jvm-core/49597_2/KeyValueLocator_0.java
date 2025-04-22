    private static Node[] handleBucketConfigRequest(GetBucketConfigRequest request, Set<Node> nodes) {
        for (Node node : nodes) {
            if (node.isState(LifecycleState.CONNECTED)) {
                if (!request.hostname().equals(node.hostname())) {
                    continue;
                }
                return new Node[] { node };
            }
        }
        return EMPTY_NODES;
    }

