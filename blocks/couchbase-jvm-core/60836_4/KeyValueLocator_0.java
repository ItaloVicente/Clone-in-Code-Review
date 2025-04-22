        if(handleNotEqualNodeSizes(config.nodes().size(), nodes.size())) {
            RetryHelper.retryOrCancel(env, request, responseBuffer);
            return;
        }

        throw new IllegalStateException("Node not found for request" + request);
    }

    private static boolean handleNotEqualNodeSizes(int configNodeSize, int actualNodeSize) {
        if (configNodeSize != actualNodeSize) {
