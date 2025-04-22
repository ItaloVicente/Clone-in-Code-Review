        int nodeSize = nodes.size();
        int offset = (int) counter++ % nodeSize;

        for (int i = offset; i < nodeSize; i++) {
            Node node = nodes.get(i);
            if (checkNode(node, (CouchbaseBucketConfig) bucketConfig)) {
                return new Node[] { node };
            }
        }

        for (int i = 0; i < offset; i++) {
            Node node = nodes.get(i);
            if (checkNode(node, (CouchbaseBucketConfig) bucketConfig)) {
