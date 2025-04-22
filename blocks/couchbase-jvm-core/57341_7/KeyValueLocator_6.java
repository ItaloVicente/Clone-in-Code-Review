    private static Node[] firstConnectedNode(Set<Node> nodes) {
        for (Node node : nodes) {
            if (node.isState(LifecycleState.CONNECTED)) {
                return new Node[] { node };
            }
        }
        return EMPTY_NODES;
    }

