
        nodes = filterNodes(nodes);
        if (nodes.isEmpty()) {
            RetryHelper.retryOrCancel(env, request, responseBuffer);
            return;
        }

