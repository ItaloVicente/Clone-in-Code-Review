
        for (int i = offset; i < nodeSize; i++) {
            Node node = nodes.get(i);
            if (checkNode(node, (CouchbaseBucketConfig) bucketConfig)) {
                node.send(request);
                return;
            }
