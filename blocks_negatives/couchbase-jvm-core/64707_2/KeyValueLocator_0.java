    /**
     * Returns first node in {@link LifecycleState#CONNECTED} state
     *
     * @param nodes the nodes to iterate
     */
    private static void firstConnectedNode(CouchbaseRequest request, List<Node> nodes, CoreEnvironment env,
        RingBuffer<ResponseEvent> responseBuffer) {
        for (Node node : nodes) {
            if (node.isState(LifecycleState.CONNECTED)) {
                node.send(request);
                return;
            }
        }
        RetryHelper.retryOrCancel(env, request, responseBuffer);
    }

