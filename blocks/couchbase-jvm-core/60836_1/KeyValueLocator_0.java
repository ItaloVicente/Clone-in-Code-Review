        if(handleNotEqualNodeSizes(config.nodes().size(), nodes.size(), request, env, responseBuffer)) {
            return;
        }

        throw new IllegalStateException("Node not found for request" + request);
    }

    private static boolean handleNotEqualNodeSizes(int configNodeSize, int actualNodeSize, final BinaryRequest request,
        CoreEnvironment env, RingBuffer<ResponseEvent> responseBuffer) {
        if (configNodeSize != actualNodeSize) {
