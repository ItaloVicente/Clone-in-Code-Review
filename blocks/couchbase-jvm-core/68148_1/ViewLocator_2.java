        nodes = filterNodes(nodes, (CouchbaseBucketConfig) bucketConfig);
        if (nodes.isEmpty()) {
            RetryHelper.retryOrCancel(env, request, responseBuffer);
            return;
        }

