    private static Node[] handleStatRequest(StatRequest request, Set<Node> nodes) {
        List<Node> connectedNodes = new ArrayList<Node>(nodes.size());
        for (Node node : nodes) {
            if (node.isState(LifecycleState.CONNECTED)) {
                connectedNodes.add(node);
            }
        }
        return connectedNodes.toArray(new Node[connectedNodes.size()]);
    }

